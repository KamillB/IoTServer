package com.example.iotserver.main.rest;

import com.example.iotserver.main.models.db.Device;
import com.example.iotserver.main.models.db.Image;
import com.example.iotserver.main.models.db.Temperature;
import com.example.iotserver.main.models.db.TemperatureArchive;
import com.example.iotserver.main.models.dbModels.DeviceModel;
import com.example.iotserver.main.models.dbModels.ImageModel;
import com.example.iotserver.main.models.dbModels.TemperatureModel;
import com.example.iotserver.main.repository.*;
import com.example.iotserver.main.utils.UniqueKeyGenerator;
import com.example.iotserver.main.websocket.WebSocketClientHandler;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/rpi")
public class RpiDataRestController {

    @Autowired
    private TemperatureRepository temperatureRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private TemperatureArchiveRepository temperatureArchiveRepository;
    @Autowired
    private DeviceKeyRepository deviceKeyRepository;
    @Autowired
    private ImageRepository imageRepository;

    @PostMapping("register")
    public Device registerRpi(@RequestBody DeviceModel input){
        Boolean unique = true;
        Iterable<Device> devices = deviceRepository.findAll();
        for (Device dev : devices){
            if (dev.getSerialNumber().equals(input.getSerialNumber())){
                unique = false;
            }
        }
        if (unique){
            Device device = new Device();
            device.setSerialNumber(input.getSerialNumber());
            device.setMac(input.getMac());
            device.setDeviceKey(UniqueKeyGenerator.generate());

            deviceRepository.save(device);
            return device;
        }

        return null;
    }

    @PostMapping("changeKey")
    public Device changeKey(@RequestBody DeviceModel input){
        Iterable<Device> devices = deviceRepository.findAll();
        for (Device dev : devices){
            if (dev.getSerialNumber().equals(input.getSerialNumber())){
                dev.setDeviceKey(UniqueKeyGenerator.generate());
                deviceRepository.save(dev);
                return dev;
            }
        }
        return null;
    }

    @PostMapping("temperature")
    public TemperatureArchive saveTemperature(@RequestBody TemperatureModel input){
        TemperatureArchive temp_arch = new TemperatureArchive(
                input.getOwnerSerialNumber(),
                input.getTemp(),
                new Date(input.getMilis() * 1000),
                input.getName()
           );
        temperatureArchiveRepository.save(temp_arch);

        Boolean unique = true;
        Iterable<Temperature> temps = temperatureRepository.findAll();
        for (Temperature t : temps){
            if (t.getName().equals(input.getName())){
                if (t.getOwner().equals(input.getOwnerSerialNumber())){
                    t.setTemp(temp_arch.getTemp());
                    t.setDate(temp_arch.getDate());
                    temperatureRepository.save(t);
                    unique = false;
                }
            }
        }
        if (unique) {
            Temperature temp = new Temperature(
                    temp_arch.getOwner(),
                    temp_arch.getTemp(),
                    temp_arch.getDate(),
                    temp_arch.getName()
            );
            temperatureRepository.save(temp);
        }
        return temp_arch;
    }

    @PostMapping("image")
    public Image saveImage(@RequestBody ImageModel input){
        // Create thumbnail
        BufferedImage img = null;
        byte[] photo = Base64.getDecoder().decode(input.getImage());
        try {
            img = ImageIO.read(new ByteArrayInputStream(photo));
        }
        catch (Exception e){}

        // if not unique override old record with new img/thumbnail/date
        Boolean unique = true;
        Iterable<Image> images = imageRepository.findAll();
        for (Image i : images){
            if (i.getName().equals(input.getName())){
                if (i.getOwner().equals(input.getOwner())){
                    i.setDate(new Date(input.getMilis() * 1000));
                    i.setImage(photo);

                    imageRepository.save(i);
                    unique = false;
                    return i;
                }
            }
        }

        // if unique create new record
        if (unique){
            Image image = new Image(
                    input.getOwner(),
                    photo,
                    new Date(input.getMilis() * 1000),
                    input.getName()
            );

            imageRepository.save(image);
            return image;
        }

            return null;
    }
}
