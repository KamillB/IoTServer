package com.example.iotserver.main.models.db;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "image")
@EntityListeners(AuditingEntityListener.class)
public class Image {
    @Id
    @SequenceGenerator(name = "IdGenerator", sequenceName = "basicSequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "IdGenerator", strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Column(name="Owner")
    @NotBlank
    private String owner;

    @Column(name="Image")
    private byte[] image;

    @Column
    private byte[] thumbnail;

    @Column(name="Date")
    private Date date;

    @Column(name="name")
    private String name;

    Image(){
    }

    public Image(@NotBlank String owner, byte[] image, byte[] thumbnail, Date date, String name) {
        this.owner = owner;
        this.image = image;
        this.thumbnail = thumbnail;
        this.date = date;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public byte[] getImage() {
        return image;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }
}
