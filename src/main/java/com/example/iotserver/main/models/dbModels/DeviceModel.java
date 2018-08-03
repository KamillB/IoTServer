package com.example.iotserver.main.models.dbModels;

import org.springframework.stereotype.Component;

@Component
public class DeviceModel {
    private String serialNumber;
    private String mac;

    public DeviceModel(){
    }

    public DeviceModel(String serialNumber, String mac) {
        this.serialNumber = serialNumber;
        this.mac = mac;
    }

    public String getSerialNumber() { return serialNumber; }

    public String getMac() { return mac; }

    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public void setMac(String mac) { this.mac = mac; }
}
