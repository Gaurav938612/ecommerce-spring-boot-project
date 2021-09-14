package com.example.ecommerce.services;

import com.example.ecommerce.models.Customer;
import com.example.ecommerce.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{


    CustomerRepo customerRepo;

    @Autowired
    public CustomerServiceImpl(CustomerRepo customerRepo){
        this.customerRepo=customerRepo;
    }

    @Override
    public Customer registerCustomer(Customer customer) throws SQLException {
        Optional<Customer> result=customerRepo.findByEmail(customer.getEmail());
        if(result.isPresent())
            throw new SQLException("Email already registerd");
        customerRepo.save(customer);
        return customer;
    }

    @Override
    public String login(String email, String password) {
        Optional<Customer> result=customerRepo.findByEmail(email);
        if(result.isPresent()){
            Optional<Customer> c1=customerRepo.findByEmailAndPassword(email,password);
            if(c1.isPresent())
                return "done";
            else
                return "invalid credentials";
        }
        else
            return "email not registerd";
    }

    @Override
    public Customer getCustomer(String email) {
        Optional<Customer> result=customerRepo.findByEmail(email);
        if(result.isPresent())
            return  result.get();
        else
            throw new RuntimeException("User not found");
    }
}
