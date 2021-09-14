package com.example.ecommerce.repository;

import com.example.ecommerce.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepo extends JpaRepository<ProductCategory,Integer> {
    ProductCategory findFirstByCategoryName(String category_name);
}
