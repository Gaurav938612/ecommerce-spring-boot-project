package com.example.ecommerce.services;

import com.example.ecommerce.models.Customer;
import com.example.ecommerce.models.Orders;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {

    String orderItems(String email,int addressId,String paymentType) throws SQLException;

    List<Orders> getMyOrders(int cust_id) throws SQLException;

    void cancelOrder(int ord_id, Customer customer) throws SQLException;

    List<Orders> viewMyOrders(int id);
}
