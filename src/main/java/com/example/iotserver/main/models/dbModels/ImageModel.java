package com.example.iotserver.main.models.dbModels;

public class ImageModel {
    private String ownerSerialNumber;
    private String image;
    private Long milis;
    private String name;

    public ImageModel(){
    }
    public ImageModel(String ownerSerialNumber, String image, Long milis, String name) {
        this.ownerSerialNumber = ownerSerialNumber;
        this.image = image;
        this.milis = milis;
        this.name = name;
    }

    public String getOwnerSerialNumber() {
        return ownerSerialNumber;
    }

    public String getImage() {
        return image;
    }

    public Long getMilis() {
        return milis;
    }

    public String getName() {
        return name;
    }

    public void setOwnerSerialNumber(String ownerSerialNumber) {
        this.ownerSerialNumber = ownerSerialNumber;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setMilis(Long milis) {
        this.milis = milis;
    }

    public void setName(String name) {
        this.name = name;
    }
}
