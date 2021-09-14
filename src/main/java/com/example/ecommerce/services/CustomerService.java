package com.example.ecommerce.services;

import com.example.ecommerce.models.Customer;
import org.springframework.stereotype.Service;

import java.sql.SQLException;


public interface CustomerService {

    Customer registerCustomer(Customer customer) throws SQLException;

    String login(String email, String password);

    Customer getCustomer(String email) throws RuntimeException;
}
