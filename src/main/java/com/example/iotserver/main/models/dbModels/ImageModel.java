package com.example.iotserver.main.models.dbModels;

public class ImageModel {
    private String owner;
    private String image;
    private String thumbnail;
    private Long milis;
    private String name;

    public ImageModel(){
    }
    public ImageModel(String owner, String image, Long milis, String name) {
        this.owner = owner;
        this.image = image;
        this.milis = milis;
        this.name = name;
    }

    public ImageModel(String owner, String image, Long milis, String name, String thumbnail) {
        this.owner = owner;
        this.image = image;
        this.milis = milis;
        this.name = name;
        this.thumbnail = thumbnail;
    }


    public String getOwner() {
        return owner;
    }

    public String getImage() {
        return image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Long getMilis() {
        return milis;
    }

    public String getName() {
        return name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setMilis(Long milis) {
        this.milis = milis;
    }

    public void setName(String name) {
        this.name = name;
    }
}
