package com.example.iotserver.main.models;

//import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Entity
@Table(name = "Temperature", schema = "public")
@EntityListeners(AuditingEntityListener.class)
public class TempData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @NotBlank
    @Column(name="Owner")
    private String name;

    @NotBlank
    @Column(name="Value")
    private Double temp;

    @NotBlank
    @Column(name="Date")
    private Date data;

    public TempData(){
    }

    public TempData(Integer id, String name, Double temp, Date data) {
        this.id = id;
        this.name = name;
        this.temp = temp;
        this.data = data;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public void setData(Date data) { this.data = data; }

    public Integer getId() { return id; }

    public String getName() {
        return name;
    }

    public Double getTemp() {
        return temp;
    }

    public Date getData() { return data; }
}
