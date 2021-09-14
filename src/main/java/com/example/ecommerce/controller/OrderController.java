package com.example.ecommerce.controller;

import com.example.ecommerce.helper.JwtUtil;
import com.example.ecommerce.models.Customer;
import com.example.ecommerce.models.JsonResp;
import com.example.ecommerce.models.OrderRequestModel;
import com.example.ecommerce.models.Orders;
import com.example.ecommerce.services.CustomerService;
import com.example.ecommerce.services.OrderService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLDataException;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin("http://localhost:3000/")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private JwtUtil jwtUtil;


    @PutMapping("/order-cart-items")
    public ResponseEntity<JsonResp> orderCartItems(@RequestParam("token") String token,
                                                   @RequestParam("addressId")int addressId,
                                                   @RequestParam("paymentType")String paymentType){
        System.out.println("order request from cutomer ");
        try{
            String email=jwtUtil.extractUsername(token);
            String message=orderService.orderItems(email,addressId,paymentType);
            return ResponseEntity.ok(new JsonResp(message));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PutMapping("/cancel-my-order")
    public ResponseEntity<String> cancelOrder(@RequestParam("orderId")int ord_id,
                                              @RequestParam("token")String token){
        try{
            String email=jwtUtil.extractUsername(token);
            Customer customer=customerService.getCustomer(email);
            orderService.cancelOrder(ord_id,customer);
            return ResponseEntity.ok("order cancelled successfully..");
        }catch (SQLDataException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (SecurityException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/view-my-orders/{token}")
    public ResponseEntity<List<Orders>> viewAllOrders(@PathVariable("token") String token){
        try {
            String email=jwtUtil.extractUsername(token);
            Customer customer=customerService.getCustomer(email);
            List<Orders> orders=orderService.viewMyOrders(customer.getCustomerId());
            return ResponseEntity.ok(orders);
        }
        catch (SecurityException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        catch (NoSuchElementException e){
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
