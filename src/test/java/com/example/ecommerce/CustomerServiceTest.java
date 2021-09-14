package com.example.ecommerce;

import com.example.ecommerce.models.Customer;
import com.example.ecommerce.repository.CustomerRepo;
import com.example.ecommerce.services.CustomerService;
import com.example.ecommerce.services.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

public class CustomerServiceTest {
    @Mock
    private CustomerRepo customerRepo;

    CustomerService customerService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        customerService=new CustomerServiceImpl(customerRepo);
        Customer customer=new Customer();
        customer.setCustomerId(11);
        Mockito.doReturn(Optional.of(customer)).when(customerRepo).findByEmail(anyString());
        Mockito.doReturn(Optional.empty()).when(customerRepo).findByEmailAndPassword(anyString(),anyString());
        Mockito.doReturn(Optional.of(customer)).when(customerRepo).findById(anyInt());

    }
    @Test
    public void RegisterUserTest(){
        Customer customer=new Customer();
        customer.setEmail("admin@gmail.com");
        Assertions.assertThrows(SQLException.class, ()->customerService.registerCustomer(customer));
    }

    @Test
    public void loginTest(){
        Assertions.assertEquals("invalid credentials",customerService.login("abc",
                "pass"));
    }
    @Test
    public void getCustomer(){

        Customer actual=customerService.getCustomer("abc@gmail.com");
        Assertions.assertEquals(11,actual.getCustomerId());
    }

}
