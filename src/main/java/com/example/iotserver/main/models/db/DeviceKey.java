package com.example.iotserver.main.models.db;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "device_keys")
@EntityListeners(AuditingEntityListener.class)
public class DeviceKey {
    @Id
    private String deviceKey;

    DeviceKey(){
    }

    public DeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getDeviceKey() { return deviceKey; }

    public void setDeviceKey(String deviceKey) { this.deviceKey = deviceKey; }
}
