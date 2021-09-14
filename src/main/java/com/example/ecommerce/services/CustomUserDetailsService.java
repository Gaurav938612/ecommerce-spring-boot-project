package com.example.ecommerce.services;

import com.example.ecommerce.models.Customer;
import com.example.ecommerce.models.MyUserDetails;
import com.example.ecommerce.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Customer> res=customerRepo.findByEmail(userName);
        if(res.isPresent()){
            Customer customer=res.get();
            return new MyUserDetails(customer);
        }
        else{
            throw new UsernameNotFoundException("user not found..");
        }

    }
}
