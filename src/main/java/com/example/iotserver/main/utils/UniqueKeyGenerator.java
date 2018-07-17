package com.example.iotserver.main.utils;

import com.example.iotserver.main.models.DeviceKey;
import com.example.iotserver.main.repository.DeviceKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UniqueKeyGenerator {
    private static DeviceKeyRepository deviceKeyRepository;

    @Autowired
    public void setDeviceKeyRepository(DeviceKeyRepository deviceKeyRepository) {
        UniqueKeyGenerator.deviceKeyRepository = deviceKeyRepository;
    }

    private static final Integer DEFAULT_LENGTH = 8;

    public static String generate(){
        return generate(DEFAULT_LENGTH);
    }

    public static String generate(Integer length){
        if (length>32){
            length = 32;
        }
        while (Boolean.TRUE) {
            try {
                String key = UUID.randomUUID().toString().substring(0, length);
                DeviceKey uniqueKey = new DeviceKey(key);
                deviceKeyRepository.save(uniqueKey);
                return key;
            } catch (DuplicateKeyException e) {
            }
        }
        return null;
    }
}
