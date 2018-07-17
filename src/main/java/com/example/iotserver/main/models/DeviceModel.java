package com.example.iotserver.main.models;

import org.springframework.stereotype.Component;

@Component
public class DeviceModel {
    private String serial_number;
    private String mac;

    public DeviceModel(){
    }

    public DeviceModel(String serial_number, String mac) {
        this.serial_number = serial_number;
        this.mac = mac;
    }

    public String getSerial_number() { return serial_number; }

    public String getMac() { return mac; }

    public void setSerial_number(String serial_number) { this.serial_number = serial_number; }

    public void setMac(String mac) { this.mac = mac; }
}
