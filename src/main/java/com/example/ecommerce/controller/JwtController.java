package com.example.ecommerce.controller;

import com.example.ecommerce.helper.JwtUtil;
import com.example.ecommerce.models.Customer;
import com.example.ecommerce.models.JsonResp;
import com.example.ecommerce.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:3000/")
@RestController
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService detailsService;
    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody Customer customer) throws Exception {
        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    customer.getEmail(),customer.getPassword()));
            UserDetails userDetails=this.detailsService.loadUserByUsername(customer.getEmail());
            String token=this.jwtUtil.generateToken(userDetails);
            System.out.println("JWT token is "+token);
            JsonResp jsonResp=new JsonResp();
            jsonResp.setToken(token);
            jsonResp.setMessage("done");
            return ResponseEntity.ok(jsonResp);
        }catch (UsernameNotFoundException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        catch (BadCredentialsException e){
            System.out.println(e.getMessage());
            JsonResp jsonResp=new JsonResp();
            jsonResp.setMessage("invalid");
            return ResponseEntity.ok(jsonResp);

        }
    }

}
