package com.example.iotserver.main.models;

//import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Entity
@Table(name = "Temperature")
@EntityListeners(AuditingEntityListener.class)
public class TempData {
    @Id
    @SequenceGenerator(name = "myIdGenerator", sequenceName = "mySequence", initialValue = 100, allocationSize = 100)
    @GeneratedValue(generator = "myIdGenerator", strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Column(name="Owner")
    private String owner;

    @Column(name="Value")
    private Double temp;

    @Column(name="Date")
    private Date data;

    public TempData(){
    }

    public TempData(Integer id, String owner, Double temp, Date data) {
        this.id = id;
        this.owner = owner;
        this.temp = temp;
        this.data = data;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public void setData(Date data) { this.data = data; }

    public Integer getId() { return id; }

    public String getOwner() {
        return owner;
    }

    public Double getTemp() {
        return temp;
    }

    public Date getData() { return data; }
}
