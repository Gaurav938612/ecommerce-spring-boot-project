package com.example.ecommerce;

import com.example.ecommerce.models.Orders;
import com.example.ecommerce.repository.*;
import com.example.ecommerce.services.OrderService;
import com.example.ecommerce.services.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class OrderServiceTest {
    @Mock
    private OrderRepo orderRepo;

    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private AddressRepo addressRepo;
    @Mock
    private OrderLInesRepo orderLInesRepo;
    @Mock
    private CartItemRepo cartItemRepo;

    private OrderService orderService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        orderService= new OrderServiceImpl(orderRepo,customerRepo,addressRepo,orderLInesRepo,cartItemRepo);
        Orders order=new Orders();
        Mockito.doReturn(Arrays.asList(order)).when(orderRepo).saveAll(any());
    }
    @Test
    public void orderItemTest() throws SQLException {
        ArrayList<Orders> orders=new ArrayList<>();
        orders.add(new Orders());
//        List<Orders> actual= orderService.orderItems(orders);
//        Assertions.assertEquals(orders.size(),actual.size());
    }

    @Test
    public void viewAllOrders() throws SQLException{

    }
}
