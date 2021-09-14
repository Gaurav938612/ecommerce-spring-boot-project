package com.example.ecommerce.models;

import java.util.List;

public class OrderRequestModel {
    private String token;
    private int shipmentAddressId;
    private String paymentType;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public int getShipmentAddressId() {
        return shipmentAddressId;
    }

    public void setShipmentAddressId(int shipmentAddressId) {
        this.shipmentAddressId = shipmentAddressId;
    }

}
