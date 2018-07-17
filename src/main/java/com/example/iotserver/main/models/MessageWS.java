package com.example.iotserver.main.models;

public class MessageWS {
    private String type;
    private Temperature temperature;

    public MessageWS() {}

    public MessageWS(String type, Temperature temperature) {
        this.type = type;
        this.temperature = temperature;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Temperature getTemperature() { return temperature; }
    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }
}
