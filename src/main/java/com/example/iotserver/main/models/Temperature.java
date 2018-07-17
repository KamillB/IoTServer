package com.example.iotserver.main.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "temperatures")
@EntityListeners(AuditingEntityListener.class)
public class Temperature {
    @Id
    @SequenceGenerator(name = "IdGenerator", sequenceName = "basicSequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "IdGenerator", strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Column(name="Owner")
    @NotBlank
    private String owner;

    @Column(name="Value")
    @NotNull
    private Double temp;

    @Column(name="Date")
    private Date date;

    @Column(name="name")
    private String name;

    public Temperature(){
    }

    public Temperature( String owner, Double temp, Date date, String name) {
        this.owner = owner;
        this.temp = temp;
        this.date = date;
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOwner(String owner){ this.owner = owner; }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) { this.name = name; }

    public Integer getId() {
        return id;
    }

    public String getOwner(){
        return owner;
    }

    public Double getTemp() {
        return temp;
    }

    public Date getDate() {
        return date;
    }

    public String getName() { return name; }
}
