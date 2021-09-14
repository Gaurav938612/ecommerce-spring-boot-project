package com.example.ecommerce.services;

import com.example.ecommerce.models.CartItem;

import java.sql.SQLException;
import java.util.List;

public interface CartService {
    String addToCart(int cust_id,int pro_id) throws SQLException;

    List<CartItem> getMyCarts(String email) throws SQLException;

    String manageCartItem(int cust_id, int prod_id, int quantity) throws SQLException;

    int countCartItem(int cust_id) throws SQLException;

    String updateCartItem(int cust_id, int prod_id, int quantity);

    List<CartItem> clearMyCart(String email);
}
