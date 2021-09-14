package com.example.ecommerce.repository;

import com.example.ecommerce.models.Address;
import com.example.ecommerce.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface AddressRepo extends JpaRepository<Address,Integer> {

    List<Address> findAllByCustomer(Customer c1);


    @Transactional
    @Modifying
    @Query(value = "update address set status='inactive' where address_id=?1 ",nativeQuery = true)
    int updateStatus(int addr_id);

    List<Address> findAllByCustomerAndStatus(Customer c1,String status);
}
