package com.example.iotserver.main.models.other;

import com.example.iotserver.main.models.db.*;

import java.util.List;

public class ClientData {
    private User user;
    private List<Device> devices;
    private List<Temperature> temperatures;
    private List<Image> images;
    private List<Periphery> peripheries;

    ClientData(){
    }

    public ClientData(User user, List<Device> devices, List<Temperature> temperatures, List<Image> images, List<Periphery> peripheries) {
        this.user = user;
        this.devices = devices;
        this.temperatures = temperatures;
        this.images = images;
        this.peripheries = peripheries;
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

    public List<Periphery> getPeripheries() {
        return peripheries;
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

    public void setPeripheries(List<Periphery> peripheries) {
        this.peripheries = peripheries;
    }
}
