package com.example.iotserver.main.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "device_keys")
@EntityListeners(AuditingEntityListener.class)
public class DeviceKey {
    @Id
    private String device_key;

    DeviceKey(){
    }

    public DeviceKey(String device_key) {
        this.device_key = device_key;
    }

    public String getDevice_key() { return device_key; }

    public void setDevice_key(String device_key) { this.device_key = device_key; }
}
