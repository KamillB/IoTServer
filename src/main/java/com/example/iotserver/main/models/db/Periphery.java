package com.example.iotserver.main.models.db;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "periphery")
@EntityListeners(AuditingEntityListener.class)
public class Periphery {
    @Id
    @SequenceGenerator(name = "IdGenerator", sequenceName = "basicSequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "IdGenerator", strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Column(name="Owner")
    @NotBlank
    private String owner;

    @Column(name="Date")
    private Date date;

    @Column(name="name")
    private String name;

    @Column(name="gpio_bcm")
    private Integer gpioBcm;

    @Column(name="status")
    private Integer status;

    public Periphery() {
    }

    public Periphery(@NotBlank String owner, Date date, String name, Integer gpioBcm, Integer status) {
        this.owner = owner;
        this.date = date;
        this.name = name;
        this.gpioBcm = gpioBcm;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getGpioBcm() { return gpioBcm; }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setGpioBcm(Integer gpioBcm) { this.gpioBcm = gpioBcm; }
}
