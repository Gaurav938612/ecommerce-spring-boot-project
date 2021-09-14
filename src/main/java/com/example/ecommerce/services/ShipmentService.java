package com.example.ecommerce.services;

import com.example.ecommerce.models.Address;

import java.sql.SQLException;
import java.util.List;

public interface ShipmentService {
    int addAddress(Address address) throws SQLException;

    int updateAddress(Address address) throws SQLException;

    List<Address> getAddress(int cust_id) throws SQLException;

    int deleteAddress(int addr_id) throws SQLException;
}
