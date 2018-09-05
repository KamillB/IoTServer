package com.example.iotserver.main.models.other;

public class RpiMessage {
    private String key;
    private String type;
    private Integer gpioBcm;

    public RpiMessage(){
    }

    public RpiMessage(String key, String type, Integer gpioBcm) {
        this.key = key;
        this.type = type;
        this.gpioBcm = gpioBcm;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public Integer getGpioBcm() {
        return gpioBcm;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGpioBcm(Integer gpioBcm) {
        this.gpioBcm = gpioBcm;
    }
}
