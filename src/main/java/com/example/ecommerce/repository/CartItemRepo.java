package com.example.ecommerce.repository;

import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.models.Customer;
import com.example.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem,Integer> {
    List<CartItem> findAllByCustomer(Customer customer);

    @Modifying
    @Transactional
    @Query(value = "update cart_item set quantity=" +
            "quantity+?3 where customer_id=?1 and product_id=?2",nativeQuery = true)
    void updateCartItem(int cust_id, int prod_id, int quantity);

    Optional<CartItem> findAllByCustomerAndProduct(Customer customer, Product product);
    @Modifying
    @Transactional
    @Query(value = "delete from cart_item where customer_id=?1 and product_id=?2",nativeQuery = true)
    int removeCartItem(int cust_id, int prod_id);


    void deleteAllByCustomer(Customer customer);

    @Modifying
    @Transactional
    @Query(value = "delete from cart_item where customer_id=?1",nativeQuery = true)
    void removeAllByCustomer(int cust_id);
}

