package com.example.ecommerce;

import com.example.ecommerce.models.Address;
import com.example.ecommerce.models.Customer;
import com.example.ecommerce.models.Orders;
import com.example.ecommerce.repository.*;
import com.example.ecommerce.services.ProductService;
import com.example.ecommerce.services.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

public class ProductServiceTest {

    private ProductService productService;
    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private ProductRepo productRepo;
    @Mock
    private ProductCategoryRepo productCategoryRepo;
    @Mock
    private OrderRepo orderRepo;
    @Mock
    private OrderLInesRepo orderLInesRepo;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        productService=new ProductServiceImpl(customerRepo,orderRepo,productRepo,productCategoryRepo,orderLInesRepo);
        Customer customer=new Customer();
        customer.setRoles("ADMIN");
        Mockito.doReturn(Optional.of(customer)).when(customerRepo).findById(anyInt());

        Orders orders=new Orders();
        Address address=new Address();
        Customer customer1=new Customer();
        customer1.setPassword("abc");

        address.setCustomer(customer1);
        orders.setCustomer(customer1);

        orders.setShipmentAddress(address);


        Mockito.doReturn(Arrays.asList(orders)).when(orderRepo).findAll();
    }
    @Test
    public void viewAllOrders() throws SQLException {
        List<Orders> actualList=productService.viewAllOrders();
        Assertions.assertNull(actualList.get(0).getCustomer());

        Assertions.assertNull(actualList.get(0).getShipmentAddress().getCustomer().getPassword());
    }

}
