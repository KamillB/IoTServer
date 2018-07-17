package com.example.iotserver.main.rest;

import com.example.iotserver.main.models.*;
import com.example.iotserver.main.repository.*;
import com.example.iotserver.main.utils.UniqueKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/rpi")
public class RpiDataController {

    @Autowired
    private TemperatureRepository temperatureRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private TemperatureArchiveRepository temperatureArchiveRepository;
    @Autowired
    private DeviceKeyRepository deviceKeyRepository;

    @PostMapping("register")
    public Device registerRpi(@RequestBody DeviceModel input){
        Boolean unique = true;
        Iterable<Device> devices = deviceRepository.findAll();
        for (Device dev : devices){
            if (dev.getSerial_number().equals(input.getSerial_number())){
                unique = false;
            }
        }
        if (unique){
            Device device = new Device();
            device.setSerial_number(input.getSerial_number());
            device.setMac(input.getMac());
            device.setDevice_key(UniqueKeyGenerator.generate());

            deviceRepository.save(device);
            return device;
        }

        return null;
    }

    @PostMapping("changeKey")
    public Device changeKey(@RequestBody DeviceModel input){
        Iterable<Device> devices = deviceRepository.findAll();
        for (Device dev : devices){
            if (dev.getSerial_number().equals(input.getSerial_number())){
                dev.setDevice_key(UniqueKeyGenerator.generate());
                deviceRepository.save(dev);
                return dev;
            }
        }
        return null;
    }

    @PostMapping("temperature")
    public TemperatureArchive saveTemperature(@RequestBody TemperatureModel input){
        TemperatureArchive temp_arch = new TemperatureArchive(
                input.getOwner(),
                input.getTemp(),
                new Date(input.getMilis()*1000),
                input.getName()
           );
        temperatureArchiveRepository.save(temp_arch);

        Boolean unique = true;
        Iterable<Temperature> temps = temperatureRepository.findAll();
        for (Temperature t : temps){
            if (t.getName().equals(input.getName())){
                if (t.getOwner().equals(input.getOwner())){
                    t.setTemp(input.getTemp());
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
    public void saveImage(){
    }
}
