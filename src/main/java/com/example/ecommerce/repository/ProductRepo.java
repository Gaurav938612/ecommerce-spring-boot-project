package com.example.ecommerce.repository;

import com.example.ecommerce.models.Product;
import com.example.ecommerce.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Integer> {

    @Modifying
    @Transactional
    @Query("update Product set inventory=inventory-?2 where productId=?1")
    int updateProductItemQuantity(int pro_id, int i);


    @Query(value = "select * from products where category_id=?1",nativeQuery = true)
    List<Product> findAllByProductCategory(int category_id);

}
