package com.example.ecommerce.controller;

import com.example.ecommerce.helper.JwtUtil;
import com.example.ecommerce.models.AccountData;
import com.example.ecommerce.models.Customer;
import com.example.ecommerce.models.JsonResp;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.services.CartService;
import com.example.ecommerce.services.CustomerService;
import com.example.ecommerce.services.OrderService;
import com.example.ecommerce.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:3000/")
@RestController
public class HomeController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/")
    public String home(){
        return ("<h1>Welcome Home  </h1>");
    }

    @PostMapping("/sign-up")
    public ResponseEntity<JsonResp> signUp(@RequestBody Customer customer)  {
        System.out.println("sign up request");
        String password = customer.getPassword();
        customer.setPassword(passwordEncoder.encode(password));
        try {
            customer.setRoles("ROLE_USER");
            Customer res = customerService.registerCustomer(customer);
            return ResponseEntity.ok(new JsonResp("done"));
        } catch (SQLException e) {
             e.printStackTrace();
             return  ResponseEntity.ok(new JsonResp(e.getMessage()));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/account/{token}")
    public ResponseEntity<Customer> getAccountDetails(@PathVariable("token") String token){
        System.out.println("account request came");
        try {
            String email=jwtUtil.extractUsername(token);
            return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomer(email));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
    @GetMapping("/fetchAllMyData/{token}")
    public ResponseEntity<AccountData> getAllMyData(@PathVariable("token") String token){
        System.out.println("token from getting all data"+token);
        try {
            String email=jwtUtil.extractUsername(token);
            AccountData accountData=new AccountData();
            Customer customer=customerService.getCustomer(email);

            accountData.setCustomer(customer);
            accountData.setAddresses(shipmentService.getAddress(customer.getCustomerId()));
            accountData.setCartItems(cartService.getMyCarts(customer.getEmail()));
            accountData.setOrdersList(orderService.viewMyOrders(customer.getCustomerId()));
            return ResponseEntity.ok(accountData);

        }catch (RuntimeException | SQLException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



}
