package com.example.ecommerce.services;

import com.example.ecommerce.models.Address;
import com.example.ecommerce.models.Customer;
import com.example.ecommerce.repository.AddressRepo;
import com.example.ecommerce.repository.CustomerRepo;
import com.example.ecommerce.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ShipmentServiceImpl implements ShipmentService{


    private AddressRepo addressRepo;
    private CustomerRepo customerRepo;
    private OrderRepo orderRepo;
    private String status="active";

    @Autowired
    public ShipmentServiceImpl(AddressRepo addressRepo, CustomerRepo customerRepo) {
        this.addressRepo = addressRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public int addAddress(Address address) throws SQLException {
//        System.out.println(address);
        System.out.println(address);
        Address res=addressRepo.save(address);
        return  res.getAddressId();
    }

    @Override
    public int updateAddress(Address address) throws SQLException{
        Address res=addressRepo.save(address);
        return res.getAddressId();
    }

    @Override
    public List<Address> getAddress(int cust_id) throws SQLException {
        Customer c1=customerRepo.findById(cust_id).get();
        List<Address> addresses=addressRepo.findAllByCustomerAndStatus(c1,status);
        return addresses;
    }

    @Override
    public int deleteAddress(int addr_id)  {
        int res=addressRepo.updateStatus(addr_id);
        return res;
    }
}
