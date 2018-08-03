package com.example.iotserver.main.models.db;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user_device_junction")
@EntityListeners(AuditingEntityListener.class)
public class UserDeviceJunction {
    @Id
    @SequenceGenerator(name = "jIdGenerator", sequenceName = "jIdSequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "jIdGenerator", strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Column(name="user id")
    private Integer userId;

    @Column(name="device id")
    private Integer deviceId;

    public UserDeviceJunction(){
    }

    public UserDeviceJunction(@NotBlank Integer userId, @NotBlank Integer deviceId) {
        this.userId = userId;
        this.deviceId = deviceId;
    }
    public Integer getId() { return id; }

    public Integer getUserId() { return userId; }

    public Integer getDeviceId() { return deviceId; }

    public void setId(Integer id) { this.id = id; }

    public void setUserId(Integer userId) { this.userId = userId; }

    public void setDeviceId(Integer deviceId) { this.deviceId = deviceId; }
}
