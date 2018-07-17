package com.example.iotserver.main.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user_device_junction")
@EntityListeners(AuditingEntityListener.class)
public class UserDeviceJunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Column(name="user id")
    @NotBlank
    private Integer user_id;

    @Column(name="device id")
    @NotBlank
    private Integer device_id;

    public UserDeviceJunction(){
    }

    public UserDeviceJunction(@NotBlank Integer user_id, @NotBlank Integer device_id) {
        this.user_id = user_id;
        this.device_id = device_id;
    }
    public Integer getId() { return id; }

    public Integer getUser_id() { return user_id; }

    public Integer getDevice_id() { return device_id; }

    public void setId(Integer id) { this.id = id; }

    public void setUser_id(Integer user_id) { this.user_id = user_id; }

    public void setDevice_id(Integer device_id) { this.device_id = device_id; }
}
