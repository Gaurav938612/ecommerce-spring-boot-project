package com.example.ecommerce.controller;


import com.example.ecommerce.helper.JwtUtil;
import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.models.JsonResp;
import com.example.ecommerce.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000/")
@RestController
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addToCart(@RequestParam("customer_id") int cust_id
            ,@RequestParam("product_id") int pro_id){
        try{
            String res=cartService.addToCart(cust_id,pro_id);
            return ResponseEntity.ok(res);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("unable to add to cart");
        }
    }
    @PutMapping("/clear-my-cart")
    public ResponseEntity<List<CartItem>> clearMyCart(@RequestParam("token") String token){
        try{
            String email=jwtUtil.extractUsername(token);
            List<CartItem> cartItems=cartService.clearMyCart(email);
            return ResponseEntity.ok(cartItems);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/view-my-cart/{token}")
    public ResponseEntity<List<CartItem>> viewMyCart(@PathVariable("token") String token){
        try{
            String email=this.jwtUtil.extractUsername(token);
            List<CartItem> myCarts=cartService.getMyCarts(email);
            return ResponseEntity.ok(myCarts);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/manage-cart-item")
    public ResponseEntity<JsonResp> manageCartItem(@RequestParam("customer_id")int cust_id,
                                                   @RequestParam("product_id") int prod_id, @RequestParam("quantity") int quantity){
        try{
            String response=cartService.manageCartItem(cust_id,prod_id,quantity);
            return ResponseEntity.ok(new JsonResp(response));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/update-cart-item")
    public ResponseEntity<JsonResp> updateCartItem(@RequestParam("customer_id")int cust_id,
                                                   @RequestParam("product_id") int prod_id, @RequestParam("quantity") int quantity){
        System.out.println("requst came for update cart");
        try{
            String response=cartService.updateCartItem(cust_id,prod_id,quantity);
            return ResponseEntity.ok(new JsonResp(response));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/count-cart-items/{id}")
    public int getCartItemCount(@PathVariable("id") int cust_id){
        try{
            return cartService.countCartItem(cust_id);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

}
