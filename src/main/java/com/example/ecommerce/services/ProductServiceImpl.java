package com.example.ecommerce.services;

import com.example.ecommerce.models.*;
import com.example.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    private CustomerRepo customerRepo;
    private OrderRepo orderRepo;
    private ProductRepo productRepo;
    private ProductCategoryRepo productCategoryRepo;



    private OrderLInesRepo orderLInesRepo;

    @Autowired
    public ProductServiceImpl(CustomerRepo customerRepo, OrderRepo orderRepo, ProductRepo productRepo, ProductCategoryRepo productCategoryRepo, OrderLInesRepo orderLInesRepo) {
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.productCategoryRepo = productCategoryRepo;
        this.orderLInesRepo = orderLInesRepo;
    }

    @Override
    public Product addProduct(Product product) {


        ProductCategory category= productCategoryRepo.findFirstByCategoryName(product.getCategory_name());
        if(category==null){
            ProductCategory p= productCategoryRepo.save(new ProductCategory(product.getCategory_name()));
            product.setCategory(p);
        }
        else {
            product.setCategory(category);
        }
        Product result= productRepo.save(product);

        return result;
    }

    @Override
    public Product updateProduct(Product product, int id) {

        Optional<Product> op=productRepo.findById(id);
        if(!op.isPresent())
            throw new RuntimeException("product not found for update");
        Product product2= op.get();
        String c=product.getCategory_name();
        ProductCategory res= productCategoryRepo.findFirstByCategoryName(c);
        if(res==null){
            res=new ProductCategory(c);
            productCategoryRepo.save(res);
        }
        product2.setTitle(product.getTitle());
        product2.setPrice(product.getPrice());
        product2.setDescription(product.getDescription());
        product2.setInventory(product.getInventory());
        product2.setImage(product.getImage());
        product2.setCategory(res);
        Product result=productRepo.save(product2);
        return result;
    }

    @Override
    public void saveImageFile(int id, MultipartFile file) throws IOException {
        Product product=productRepo.findById(id).get();
        Byte[] byteObj=new Byte[file.getBytes().length];
        int i=0;
        for (byte b: file.getBytes()){
            byteObj[i++]=b;
        }
        product.setImage(byteObj);
        productRepo.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
    @Override
    public List<Product> getAllProductsByCategory(int category_id) {
        return productRepo.findAllByProductCategory(category_id);
    }

    @Override
    public Orders viewAnOrder(int id) {
        Orders order=orderRepo.findById(id).get();
        order.setCustomer(null);
        order.getShipmentAddress().getCustomer().setPassword(null);
        return  order;
    }

    @Override
    public Product getProduct(int id) {
        return productRepo.findById(id).get();
    }

    @Override
    public List<ProductCategory> getCatalogs() throws SQLException {
        return productCategoryRepo.findAll();
    }
    @Override
    public List<Orders> viewAllOrders() throws SQLException {
        List<Orders> orders = orderRepo.findAll();
        for (Orders ord : orders) {
            ord.setCustomer(null);
            ord.getShipmentAddress().getCustomer().setPassword(null);
        }
        return orders;
    }

    @Override
    public String respondOrder(int ord_id, String response) throws SQLException {
        List<String> responses=new ArrayList<>();
        responses.add("CANCELLED");
        responses.add("CONFIRMED");
        responses.add("SHIPPED");

        int res;
        if(responses.contains(response))
            res=orderRepo.updateOrderItem(ord_id,response);
        else
            throw new RuntimeException("Invalid response");
        if(res==0)
            throw new SQLDataException("order not found ");
        else
            return "ORDER SUCCESSFULLY "+response;

    }

    @Override
    public List<OrderLines> getOrderDetails(int id) {
        Orders orders=orderRepo.findById(id).get();
        List<OrderLines> orderLines=orderLInesRepo.findAllByOrders(orders);
        return orderLines;
    }




}
