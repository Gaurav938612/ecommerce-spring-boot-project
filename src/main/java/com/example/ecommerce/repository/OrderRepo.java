package com.example.ecommerce.repository;

import com.example.ecommerce.models.Customer;
import com.example.ecommerce.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepo extends JpaRepository<Orders,Integer> {

    @Query(value = "select * from orders where customer_id=?1",nativeQuery = true)
    List<Orders> findAllByCustId(int cust_id);

    @Modifying
    @Transactional
    @Query(value = "update orders set status=?2 where order_id=?1",nativeQuery = true)
    int updateOrderItem(int ord_id, String cancelled);

}
