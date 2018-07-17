package com.example.iotserver.main.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @SequenceGenerator(name = "IdGenerator", sequenceName = "basicSequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "IdGenerator", strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Column(name="name")
    @NotBlank
    private String name;

    @Column(name="mail")
    @NotBlank
    private String mail;

    @Column(name="password")
    @NotBlank
    private String password;

    public User(){
    }

    public User(@NotBlank String name, @NotBlank String mail, @NotBlank String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    public Integer getId() { return id; }

    public String getName() { return name; }

    public String getMail() { return mail; }

    public String getPassword() { return password; }

    public void setId(Integer id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setMail(String mail) { this.mail = mail; }

    public void setPassword(String password) { this.password = password; }
}
