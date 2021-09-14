package com.example.ecommerce.controller;

import com.example.ecommerce.helper.JwtUtil;
import com.example.ecommerce.models.Address;
import com.example.ecommerce.models.Customer;
import com.example.ecommerce.models.JsonResp;
import com.example.ecommerce.services.CustomerService;
import com.example.ecommerce.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000/")
@RestController
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add-address")
    public ResponseEntity<JsonResp> addAddress(@RequestBody Address address) throws Exception{
        try{
            int res_id=shipmentService.addAddress(address);
            return ResponseEntity.ok(new JsonResp(Integer.toString(res_id)));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JsonResp(Integer.toString(-1)));
        }
    }
    @PutMapping("/update-address")
    public ResponseEntity<JsonResp> editAddress(@RequestBody Address address) throws Exception{
        try{
            int res=shipmentService.updateAddress(address);
            return ResponseEntity.ok(new JsonResp(Integer.toString(res)));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JsonResp(Integer.toString(-1)));
        }
    }

    @GetMapping("/fetch-address/{token}")
    public ResponseEntity<List<Address>> getAddress(@PathVariable("token") String token){
        try{
            String email=jwtUtil.extractUsername(token);
            Customer customer=customerService.getCustomer(email);
            List<Address> addresses=shipmentService.getAddress(customer.getCustomerId());
            return ResponseEntity.ok(addresses);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete-address")
    public ResponseEntity<JsonResp> deleteAddress(@RequestParam("address_id") int addr_id){
        try{
           int res=shipmentService.deleteAddress(addr_id);
            System.out.println(res);
           if(res==1)
               return ResponseEntity.ok(new JsonResp("deleted"));
           else{
               return ResponseEntity.ok(new JsonResp("cannot be deleted"));
           }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(new JsonResp("cannot be deleted"));
        }
    }

}
