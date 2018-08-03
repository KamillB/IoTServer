package com.example.iotserver.main.models;

import com.example.iotserver.main.models.db.*;

import java.util.List;

public class ClientData {
    private User user;
    private List<Device> devices;
    private List<Temperature> temperatures;
    private List<Image> images;

    ClientData(){
    }

    public ClientData(User user, List<Device> devices, List<Temperature> temperatures, List<Image> images) {
        this.user = user;
        this.devices = devices;
        this.temperatures = temperatures;
        this.images = images;
    }

    public User getUser() {
        return user;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public List<Temperature> getTemperatures() {
        return temperatures;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public void setTemperatures(List<Temperature> temperatures) {
        this.temperatures = temperatures;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
