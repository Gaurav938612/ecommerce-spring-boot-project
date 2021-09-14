package com.example.ecommerce.repository;

import com.example.ecommerce.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByEmailAndPassword(String email,String password);

    @Query(value = "select count(*) from cart_item where customer_id=?1",nativeQuery = true)
    int countDictinctItem(int cust_id);
}
