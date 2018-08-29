package com.example.iotserver.main.models.db;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

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
    private String serialNumber;

    @Column(name = "mac")
    private String mac;

    @Column(name="device key")
    private String deviceKey;

    @Column(name="ip address")
    private String ip;

    public Device(){
    }

    public Device(String serialNumber, String mac, String deviceKey, String ip) {
        this.serialNumber = serialNumber;
        this.mac = mac;
        this.deviceKey = deviceKey;
        this.ip = ip;
    }

    public Integer getId() { return id; }

    public String getSerialNumber() { return serialNumber; }

    public String getMac() { return mac; }

    public String getDeviceKey() { return deviceKey; }

    public String getIp() { return ip; }

    public void setId(Integer id) { this.id = id; }

    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public void setMac(String mac) { this.mac = mac; }

    public void setDeviceKey(String deviceKey) { this.deviceKey = deviceKey; }

    public void setIp(String ip) { this.ip = ip; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(id, device.id) &&
                Objects.equals(serialNumber, device.serialNumber) &&
                Objects.equals(mac, device.mac) &&
                Objects.equals(deviceKey, device.deviceKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, mac, deviceKey);
    }
}
