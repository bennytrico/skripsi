package com.example.skripsicustomer1;

import android.content.Intent;

public class Montir {
    private Integer wallet;
    private String name,address,email,role,bankAccount,id;
    private Double latitude,longitude;

    public Montir () {

    }
    public Montir (String name, String address,String email,String role,String bankAccount, Double latitude, Double longitude, Integer wallet) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.bankAccount = bankAccount;
        this.role = role;
        this.latitude = latitude;
        this.longitude = longitude;
        this.wallet = wallet;
    }

    public Integer getWallet() {
        return wallet;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWallet(Integer wallet) {
        this.wallet = wallet;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
