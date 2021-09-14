package com.example.ecommerce.services;

import com.example.ecommerce.models.OrderLines;
import com.example.ecommerce.models.Orders;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.models.ProductCategory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public interface ProductService {
    Product addProduct(Product product);

    Product updateProduct(Product product, int id);

    void saveImageFile(int id, MultipartFile file) throws IOException;

    List<Product> getAllProducts();

    Product getProduct(int id);

    List<ProductCategory> getCatalogs() throws SQLException;
    List<Orders> viewAllOrders() throws SQLException;

    String respondOrder(int ord_id, String response) throws SQLException;

    List<OrderLines> getOrderDetails(int id);

    List<Product> getAllProductsByCategory(int category_id);

    Orders viewAnOrder(int id);
}
