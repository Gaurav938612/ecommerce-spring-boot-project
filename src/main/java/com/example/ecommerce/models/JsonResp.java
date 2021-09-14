package com.example.ecommerce.models;

import java.util.List;

public class JsonResp {
    private String message;
    private String token;
    private List<CartItem> cartItems;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JsonResp() {
    }

    public JsonResp(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
