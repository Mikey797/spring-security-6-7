package com.example.springsecurity6.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String otp;

    public Otp(int id, String username, String otp) {
        this.id = id;
        this.username = username;
        this.otp = otp;
    }

    public Otp(String username, String otp) {
        this.username = username;
        this.otp = otp;
    }

    public Otp() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String password) {
        this.otp = password;
    }
}
