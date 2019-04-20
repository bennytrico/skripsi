package com.example.skripsicustomer1;

public class Order {
    private String customer_id,address,type_order,transmition,brand,type_motor,date,time,status_order,name_customer,
            no_handphone_customer,number_plate;
    private Boolean primary_oil,secondary_oil,flag_customer_agree,flag_montir_agree;
    private Integer amount;

    private Montir montir;

    public Order (String customer_id,String address, String type_order, String transmition,
                  String brand, String type_motor, String date, String time,String status_order,Boolean oli_ganda,
                  Boolean oli_mesin, Boolean flag_customer_agree,Boolean flag_montir_agree,Integer amount,Montir montir,
                  String name_customer, String no_handphone_customer,String number_plate) {
        this.customer_id = customer_id;
        this.address = address;
        this.type_order = type_order;
        this.transmition = transmition;
        this.brand = brand;
        this.type_motor = type_motor;
        this.date = date;
        this.time = time;
        this.status_order = status_order;
        this.secondary_oil = oli_ganda;
        this.primary_oil = oli_mesin;
        this.flag_customer_agree = flag_customer_agree;
        this.flag_montir_agree = flag_montir_agree;
        this.amount = amount;
        this.montir = montir;
        this.name_customer = name_customer;
        this.no_handphone_customer = no_handphone_customer;
        this.number_plate = number_plate;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType_order() {
        return type_order;
    }

    public void setType_order(String type_order) {
        this.type_order = type_order;
    }

    public String getTransmition() {
        return transmition;
    }

    public void setTransmition(String transmition) {
        this.transmition = transmition;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType_motor() {
        return type_motor;
    }

    public void setType_motor(String type_motor) {
        this.type_motor = type_motor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus_order() {
        return status_order;
    }

    public void setStatus_order(String status_order) {
        this.status_order = status_order;
    }

    public Boolean getOli_mesin() {
        return primary_oil;
    }

    public void setOli_mesin(Boolean oli_mesin) {
        this.primary_oil = oli_mesin;
    }

    public Boolean getOli_ganda() {
        return secondary_oil;
    }

    public void setOli_ganda(Boolean oli_ganda) {
        this.secondary_oil = oli_ganda;
    }

    public Boolean getFlag_customer_agree() {
        return flag_customer_agree;
    }

    public void setFlag_customer_agree(Boolean flag_customer_agree) {
        this.flag_customer_agree = flag_customer_agree;
    }

    public Boolean getFlag_montir_agree() {
        return flag_montir_agree;
    }

    public void setFlag_montir_agree(Boolean flag_montir_agree) {
        this.flag_montir_agree = flag_montir_agree;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Montir getMontir() {
        return montir;
    }

    public void setMontir(Montir montir) {
        this.montir = montir;
    }

    public String getName_customer() {
        return name_customer;
    }

    public void setName_customer(String name_customer) {
        this.name_customer = name_customer;
    }

    public String getNo_handphone_customer() {
        return no_handphone_customer;
    }

    public void setNo_handphone_customer(String no_handphone_customer) {
        this.no_handphone_customer = no_handphone_customer;
    }

    public String getNumber_plate() {
        return number_plate;
    }

    public void setNumber_plate(String number_plate) {
        this.number_plate = number_plate;
    }
}
