package com.example.iotserver.main.websocket;


import ch.qos.logback.core.net.server.Client;
import com.example.iotserver.main.models.other.ClientData;
import com.example.iotserver.main.models.db.*;
import com.example.iotserver.main.models.dbModels.*;
import com.example.iotserver.main.models.other.RpiMessage;
import com.example.iotserver.main.models.wsModels.*;
import com.example.iotserver.main.repository.*;
import com.google.gson.Gson;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class WebSocketClientHandler extends TextWebSocketHandler {

    @Autowired
    LoggingSessionKeyRepository loggingSessionKeyRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    UserDeviceJunctionRepository userDeviceJunctionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TemperatureRepository temperatureRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    PeripheryRepository peripheryRepository;
    @Autowired
    TemperatureArchiveRepository temperatureArchiveRepository;

    private Gson gson = new Gson();
    private List<WebSocketSession> sessions = new ArrayList<>();
    private Map<WebSocketSession, ClientData> sessionClientDataList = new HashMap<WebSocketSession, ClientData>();
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
        WsMessage wsMessage = gson.fromJson(message.getPayload(), WsMessage.class);
        String type = wsMessage.getType();
        String mail = null;
        User user = null;

        try {
            System.out.println(wsMessage.getSessionKey() + "\n" + wsMessage.getType() + "\n" + wsMessage.getPayload().toString());
            LoggingSessionKey key = loggingSessionKeyRepository.findBySessionTemporaryKey(wsMessage.getSessionKey());
            mail = key.getMail();
            user = userRepository.findByMail(mail);

            switch (type){
                case "initialMessage" :
                    getMainPageData(session, user);
                    break;

                case "refresh" :
                    getMainPageData(session, user);
                    break;

                case "addDevice" :
                    String deviceKey = wsMessage.getPayload().getDeviceKey();
                    System.out.println("Device key is " + deviceKey);
                    addDeviceToUser(session, deviceKey);
                    break;

                case "periphery" :
                    PeripheryModel periphery = wsMessage.getPayload().getPeripheryModels().get(0);
                    switchPeripheryStatus(periphery);
                    break;

                case "temperatureArchive" :
                    getTemperatureArchiveData(session, wsMessage.getPayload().getTemperatureModel().get(0));
                    break;
            }
        }
        catch (Exception e){
            WsMessage reply = new WsMessage();
            reply.setType("sessionKeyError");
            session.sendMessage(new TextMessage(gson.toJson(reply)));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //executor.scheduleAtFixedRate(checkIfDatabaseRecordChanged, 0, 5, TimeUnit.SECONDS);

        //////////////////////
        sessions.add(session);
        System.out.println("OPENED" + sessions);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //executor.shutdown();

        //////////////////////
        sessions.remove(session);
        System.out.println("CLOSED" + sessions + "close status " + status);
    }

    private void getMainPageData(WebSocketSession session, User user) {
        try {
            cacheNewUser(session, user);
            sendInitialData(session);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void cacheNewUser(WebSocketSession session, User user) {
        // Devices list for user
        Iterable<UserDeviceJunction> userDeviceJunctions = userDeviceJunctionRepository.findAllByUserId(user.getId());
        List<Device> userDevices = new ArrayList<>();
        for (UserDeviceJunction udj : userDeviceJunctions) {
            Optional<Device> device = deviceRepository.findById(udj.getDeviceId());
            if (device.isPresent()){
                userDevices.add(device.get());
            }
        }

        // Temperature, Image and Peripheries list for user
        List<Temperature> userTemperatures = new ArrayList<>();
        List<Image> userImages = new ArrayList<>();
        List<Periphery> userPeripheries = new ArrayList<>();
        for (Device dev: userDevices) {
            Iterable<Temperature> temp = temperatureRepository.findAllByOwner(dev.getSerialNumber());
            for (Temperature t : temp) {
                userTemperatures.add(t);
            }

            Iterable<Image> img = imageRepository.findAllByOwner(dev.getSerialNumber());
            for (Image i : img){
                userImages.add(i);
            }

            Iterable<Periphery> periph = peripheryRepository.findAllByOwner(dev.getSerialNumber());
            for (Periphery p : periph){
                userPeripheries.add(p);
            }
        }

        // Create logged in user model for faster handling
        ClientData clientData = new ClientData(
                user,
                userDevices,
                userTemperatures,
                userImages,
                userPeripheries
        );
        sessionClientDataList.put(session, clientData);
    }

    private void addDeviceToUser(WebSocketSession session, String deviceKey) throws IOException{
        Device device = deviceRepository.findByDeviceKey(deviceKey);

        Boolean unique = true;
        for (Device dev : sessionClientDataList.get(session).getDevices()){
            if (dev.equals(device)){
                unique = false;
            }
        }

        if (unique){
            UserDeviceJunction junction = new UserDeviceJunction();
            junction.setUserId(sessionClientDataList.get(session).getUser().getId());
            junction.setDeviceId(device.getId());
            userDeviceJunctionRepository.save(junction);

            WsMessage reply = new WsMessage();
            reply.setType("addedNewDeviceSuccessfully");
            session.sendMessage(new TextMessage(gson.toJson(reply)));
        }
        else {
            WsMessage reply = new WsMessage();
            reply.setType("addedNewDeviceFailure");
            session.sendMessage(new TextMessage(gson.toJson(reply)));
        }
    }

    private void sendInitialData(WebSocketSession session) throws IOException{
        ClientData client = sessionClientDataList.get(session);
        WsMessage message = new WsMessage();
        Payload payload = new Payload();

        message.setType("sensorList");
        message.setPayload(payload);

        // Change database models to message models
        List<TemperatureModel> temperatures = new ArrayList<>();
        for (Temperature t : client.getTemperatures()){
            temperatures.add(new TemperatureModel(
                    t.getOwner(),
                    t.getTemp(),
                    t.getDate().getTime(),
                    t.getName()
            ));
        }
        payload.setTemperatureModel(temperatures);

        List<ImageModel> images = new ArrayList<>();
        for (Image i : client.getImages()){
            images.add(new ImageModel(
                    i.getOwner(),
                    Base64.getEncoder().encodeToString(i.getImage()),
                    i.getDate().getTime(),
                    i.getName()
            ));
        }
        payload.setImageModel(images);

        List<PeripheryModel> peripheries = new ArrayList<>();
        for (Periphery p : client.getPeripheries()){
            peripheries.add(new PeripheryModel(
                p.getOwner(),
                p.getDate().getTime(),
                p.getName(),
                p.getGpioBcm(),
                p.getStatus()
            ));
        }
        payload.setPeripheryModels(peripheries);

        session.sendMessage(new TextMessage(gson.toJson(message)));
        System.out.println(gson.toJson(message));
    }

    private void switchPeripheryStatus(PeripheryModel periphery){
        Device device = deviceRepository.findBySerialNumber(periphery.getOwner());

        RpiMessage message = new RpiMessage();
        message.setType("periphery");
        message.setKey(device.getDeviceKey());
        Gson gson = new Gson();
        String json = gson.toJson(message);

        String url = "http://" + device.getIp() + ":8081/test";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        try {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(json);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            httpClient.execute(request);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            try {
                httpClient.close();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void getTemperatureArchiveData(WebSocketSession session, TemperatureModel tModel) throws Exception{
        Iterable<TemperatureArchive> temps = temperatureArchiveRepository.findAllByOwnerAndAndName(tModel.getOwnerSerialNumber(), tModel.getName());
        List<TemperatureModel> temperatureModels = new ArrayList<>();
        for (TemperatureArchive t : temps){
            temperatureModels.add(new TemperatureModel(
                    t.getOwner(),
                    t.getTemp(),
                    t.getDate().getTime(),
                    t.getName()
            ));
        }

        Payload payload = new Payload();
        payload.setTemperatureModel(temperatureModels);
        WsMessage message = new WsMessage();
        message.setPayload(payload);
        message.setType("temperatureArchiveData");

        System.out.println(gson.toJson(message));
        session.sendMessage(new TextMessage(gson.toJson(message)));
    }

    Runnable checkIfDatabaseRecordChanged = new Runnable() {
        @Override
        public void run()  {
            for (Map.Entry<WebSocketSession, ClientData> entry : sessionClientDataList.entrySet()){
                List<Device> devices = entry.getValue().getDevices();
                for (Device d : devices){
                    Optional<Device> dev = deviceRepository.findById(d.getId());
                    if (dev.isPresent()){
                        Iterable<Temperature> temperatures = temperatureRepository.findAllByOwner(dev.get().getSerialNumber());
                        Iterable<Image> images = imageRepository.findAllByOwner(dev.get().getSerialNumber());
                        Iterable<Periphery> peripheries = peripheryRepository.findAllByOwner(dev.get().getSerialNumber());

                        List<TemperatureModel> tModels = new ArrayList<>();
                        List<ImageModel> iModels = new ArrayList<>();
                        List<PeripheryModel> pModels = new ArrayList<>();

                        for (Temperature t : temperatures){
                            tModels.add(new TemperatureModel(
                                    t.getOwner(),
                                    t.getTemp(),
                                    t.getDate().getTime(),
                                    t.getName()
                            ));
                        }
                        for (Image i : images){
                            iModels.add(new ImageModel(
                                    i.getOwner(),
                                    Base64.getEncoder().encodeToString(i.getImage()),
                                    i.getDate().getTime(),
                                    i.getName()
                            ));
                        }
                        for (Periphery p : peripheries){
                            pModels.add(new PeripheryModel(
                                    p.getOwner(),
                                    p.getDate().getTime(),
                                    p.getName(),
                                    p.getGpioBcm(),
                                    p.getStatus()
                            ));
                        }

                        Payload payload = new Payload();
                        payload.setTemperatureModel(tModels);
                        payload.setImageModel(iModels);
                        payload.setPeripheryModels(pModels);

                        WsMessage message = new WsMessage();
                        message.setType("runnableSensors");
                        message.setPayload(payload);
                        try {
                            entry.getKey().sendMessage(new TextMessage(gson.toJson(message)));
                        }
                        catch (Exception e){
                        }
                    }
                }
            }
        }
    };
}
