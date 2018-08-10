package com.example.iotserver.main.websocket;


import com.example.iotserver.main.models.ClientData;
import com.example.iotserver.main.models.db.*;
import com.example.iotserver.main.models.dbModels.*;
import com.example.iotserver.main.models.wsModels.*;
import com.example.iotserver.main.repository.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

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

    private Gson gson = new Gson();
    private List<WebSocketSession> sessions = new ArrayList<>();
    private Map<WebSocketSession, ClientData> sessionClientDataList = new HashMap<WebSocketSession, ClientData>();

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


        //////////////////////
        sessions.add(session);
        System.out.println("OPENED" + sessions);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {


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

        // Temperature and Image list for user
        List<Temperature> userTemperatures = new ArrayList<>();
        List<Image> userImages = new ArrayList<>();
        for (Device dev: userDevices) {
            Iterable<Temperature> temp = temperatureRepository.findAllByOwner(dev.getSerialNumber());
            for (Temperature t : temp) {
                userTemperatures.add(t);
            }

            Iterable<Image> img = imageRepository.findAllByOwner(dev.getSerialNumber());
            for (Image i : img){
                userImages.add(i);
            }
        }

        // Create logged in user model for faster handling
        ClientData clientData = new ClientData(
                user,
                userDevices,
                userTemperatures,
                userImages
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
        message.getPayload().setTemperatureModel(temperatures);



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

        session.sendMessage(new TextMessage(gson.toJson(message)));
        System.out.println(gson.toJson(message));
    }

}
