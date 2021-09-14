package com.example.ecommerce.repository;

import com.example.ecommerce.models.OrderLines;
import com.example.ecommerce.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderLInesRepo extends JpaRepository<OrderLines,Integer> {

    List<OrderLines> findAllByOrders(Orders orders);
}
