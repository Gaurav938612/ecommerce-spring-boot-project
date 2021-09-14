package com.example.ecommerce.controller;

import com.example.ecommerce.helper.JwtUtil;
import com.example.ecommerce.models.*;
import com.example.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin("http://localhost:3000/")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add-product")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        try {
            System.out.println(product);
            Product result=productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @PostMapping("/admin/add-new-product")
    public ResponseEntity<Product> addNewProduct(@RequestParam("title") String title,
             @RequestParam("price") float price,@RequestParam("category_name") String category_name,
                 @RequestParam("inventory")int inventory,@RequestParam("description")String des,
                                                 @RequestParam(required = false,name = "image")MultipartFile file){

        try {
            Product product=new Product();
            product.setTitle(title);
            product.setCategory_name(category_name);
            product.setInventory(inventory);
            product.setDescription(des);
            product.setPrice(price);
            if(file!=null)
                product.setImage(getByteArray(file));
            Product result=productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @PutMapping("/admin/update-product")
    public ResponseEntity<Product> updateProduct(@RequestParam("title") String title,
                         @RequestParam("productId")int id,
                         @RequestParam("price") float price,@RequestParam("category_name") String category_name,
                         @RequestParam("inventory")int inventory,@RequestParam("description")String des,
                         @RequestParam(required = false,name = "image")MultipartFile file) {

        try {
            Product product=new Product();
            product.setTitle(title);
            product.setCategory_name(category_name);
            product.setInventory(inventory);
            product.setDescription(des);
            product.setPrice(price);
            if(file!=null) {
                System.out.println("image uploaded");
                product.setImage(getByteArray(file));
            }
            Product result=productService.updateProduct(product,id);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/admin/update-product-without-img")
    public ResponseEntity<Product> updateProductWithoutImg(@RequestParam("title") String title,
                                   @RequestParam("productId")int id,
                                   @RequestParam("price") float price,@RequestParam("category_name") String category_name,
                                   @RequestParam("inventory")int inventory,@RequestParam("description")String des,
                                   @RequestParam(required = false,name = "image")Byte[] imageArray) {

        try {
            Product product=new Product();
            product.setTitle(title);
            product.setCategory_name(category_name);
            product.setInventory(inventory);
            product.setDescription(des);
            product.setPrice(price);
            if(imageArray!=null){
                product.setImage(imageArray);
            }
            Product result=productService.updateProduct(product,id);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/catalogs")
    public ResponseEntity<List<ProductCategory>> getCatalogs(){
        try {
            List<ProductCategory> catalogs=productService.getCatalogs();
            return ResponseEntity.ok(catalogs);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/view-products")
    public ResponseEntity<List<Product>> viewAllProducts(){

        try {
            List<Product> products=productService.getAllProducts();
            return ResponseEntity.status(HttpStatus.OK).body(products);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/view-products-by-category/{id}")
    public ResponseEntity<List<Product>> viewAllProductsByCategory(@PathVariable("id") int category_id){
        try {
            System.out.println("view products by category reqeust came...");
            List<Product> products=productService.getAllProductsByCategory(category_id);
            return ResponseEntity.status(HttpStatus.OK).body(products);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/view-product/{id}")
    public ResponseEntity<Product> viewProduct(@PathVariable int id){
        try {
            Product product=productService.getProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("admin/view-all-orders")
    public ResponseEntity<List<Orders>> viewAllOrders(){
        try {
            List<Orders> orders=productService.viewAllOrders();
            return ResponseEntity.ok(orders);
        }
        catch (SecurityException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        catch (NoSuchElementException e){
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("fetch-an-order/{order_id}")
    public ResponseEntity<List<OrderLines>> fetchAnOrder(@PathVariable("order_id") int order_id){

        try {
            List<OrderLines> orderLines= productService.getOrderDetails(order_id);
            return ResponseEntity.ok(orderLines);
        }

        catch (NoSuchElementException e){
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("admin/fetch-an-order/{order_id}")
    public ResponseEntity<List<OrderLines>> fetchAnOrderByAdmin(@PathVariable("order_id") int order_id){

        try {
            List<OrderLines> orderLines= productService.getOrderDetails(order_id);
            return ResponseEntity.ok(orderLines);
        }

        catch (NoSuchElementException e){
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("admin/respond-order")
    public ResponseEntity<JsonResp> respondOrder(@RequestParam("order_id")int ord_id,
                                                 @RequestParam("action")String response){
        try {
//            System.out.println("request came");
            String res=productService.respondOrder(ord_id,response);
            return ResponseEntity.ok(new JsonResp(res));
        }catch (SQLDataException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/admin/view-an-order-details/{order_id}")
    public ResponseEntity<Orders> viewAnOrder(@PathVariable("order_id") int id){
        try {
            Orders order=productService.viewAnOrder(id);
            return ResponseEntity.ok(order);
        }
        catch (SecurityException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        catch (NoSuchElementException e){
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public Byte[] getByteArray(MultipartFile file) throws IOException {
        Byte[] byteObj=new Byte[file.getBytes().length];
        int i=0;
        for (byte b: file.getBytes()){
            byteObj[i++]=b;
        }
        System.out.println("loop times "+i);
        return  byteObj;
    }




}
