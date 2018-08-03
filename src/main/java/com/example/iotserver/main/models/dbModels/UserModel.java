package com.example.iotserver.main.models.dbModels;

import org.springframework.stereotype.Component;

@Component
public class UserModel {
    private String name;
    private String mail;
    private String password;

    public UserModel(){
    }

    public UserModel(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    public String getName() { return name; }

    public String getMail() { return mail; }

    public String getPassword() { return password; }

    public void setName(String name) { this.name = name; }

    public void setMail(String mail) { this.mail = mail; }

    public void setPassword(String password) { this.password = password; }
}
