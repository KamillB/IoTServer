package com.example.iotserver.main.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "devices")
@EntityListeners(AuditingEntityListener.class)
public class Device {
    @Id
    @SequenceGenerator(name = "IdGenerator", sequenceName = "basicSequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "IdGenerator", strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Column (name = "serial number")
    private String serial_number;

    @Column(name = "mac")
    private String mac;

    @Column(name="device key")
    private String device_key;

    public Device(){
    }

    public Device(String serial_number, String mac, String device_key) {
        this.serial_number = serial_number;
        this.mac = mac;
        this.device_key = device_key;
    }

    public Integer getId() { return id; }

    public String getSerial_number() { return serial_number; }

    public String getMac() { return mac; }

    public String getDevice_key() { return device_key; }

    public void setId(Integer id) { this.id = id; }

    public void setSerial_number(String serial_number) { this.serial_number = serial_number; }

    public void setMac(String mac) { this.mac = mac; }

    public void setDevice_key(String device_key) { this.device_key = device_key; }
}
