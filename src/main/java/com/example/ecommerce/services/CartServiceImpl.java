package com.example.ecommerce.services;

import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.models.Customer;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.repository.CartItemRepo;
import com.example.ecommerce.repository.CustomerRepo;
import com.example.ecommerce.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{


    private CustomerRepo customerRepo;
    private ProductRepo productRepo;
    private CartItemRepo cartItemRepo;
    @Autowired
    public CartServiceImpl(CustomerRepo customerRepo,ProductRepo productRepo,CartItemRepo cartItemRepo){
        this.customerRepo=customerRepo;
        this.productRepo=productRepo;
        this.cartItemRepo=cartItemRepo;
    }
    @Override
    public String addToCart(int cust_id,int pro_id) throws SQLException {

        int avail_quantiy=productRepo.getById(pro_id).getInventory();
        if(avail_quantiy==0)
            return "Currently not available";
        Customer customer=customerRepo.getById(cust_id);
        Product product=productRepo.getById(pro_id);
        CartItem cartItem=new CartItem();
        cartItem.setCustomer(customer);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItemRepo.save(cartItem);
        productRepo.updateProductItemQuantity(pro_id,1);

        return "added to cart";
    }

    @Override
    public List<CartItem> getMyCarts(String email) throws SQLException {
        Customer customer=customerRepo.findByEmail(email).get();
        List<CartItem> cartItems= cartItemRepo.findAllByCustomer(customer);
        for(CartItem cartItem:cartItems){
            cartItem.setCustomer(null);
        }
        return cartItems;
    }

    @Override
    public String manageCartItem(int cust_id, int prod_id, int quantity) throws SQLException {

        int avail_quantity=productRepo.findById(prod_id).get().getInventory();
        if(avail_quantity-quantity<0){
            return "not enough in inventory";
        }

        Customer customer=customerRepo.findById(cust_id).get();
        Product product=productRepo.findById(prod_id).get();
        Optional<CartItem> result=cartItemRepo.findAllByCustomerAndProduct(customer,product);
        if(result.isPresent()){
            cartItemRepo.updateCartItem(cust_id,prod_id,quantity);
        }
        else{
            CartItem cartItem=new CartItem();
            cartItem.setProduct(product);
            cartItem.setCustomer(customer);
            cartItem.setQuantity(quantity);
            cartItemRepo.save(cartItem);
        }
        int res=0;
        res=productRepo.updateProductItemQuantity(prod_id,quantity);
        System.out.println("running manage cart item.. done");
        return "done";
    }

    @Override
    public int countCartItem(int cust_id) throws SQLException {
        return customerRepo.countDictinctItem(cust_id);
    }

    @Override
    public String updateCartItem(int cust_id, int prod_id, int quantity) {

        Customer customer=customerRepo.findById(cust_id).get();
        Product product=productRepo.findById(prod_id).get();

        if(quantity==0){
            int released_quantity=cartItemRepo.findAllByCustomerAndProduct(customer,product).get().getQuantity();
            int res=cartItemRepo.removeCartItem(cust_id,prod_id);
            productRepo.updateProductItemQuantity(prod_id,(-1)*released_quantity);
            return "done";
        }

        int avail_quantity=productRepo.findById(prod_id).get().getInventory();
        if(avail_quantity-quantity<0){
            return  "not enough in inventory";
        }
        int res=0;

        Optional<CartItem> result=cartItemRepo.findAllByCustomerAndProduct(customer,product);
        if(result.isPresent()){
            CartItem item=result.get();
            if(item.getQuantity()==1 && quantity<0)
                cartItemRepo.delete(item);
            else
                cartItemRepo.updateCartItem(cust_id,prod_id,quantity);
        }
//        else{
//            CartItem cartItem=new CartItem();
//            cartItem.setProduct(product);
//            cartItem.setCustomer(customer);
//            cartItem.setQuantity(quantity);
//            cartItemRepo.save(cartItem);
//        }

        res=productRepo.updateProductItemQuantity(prod_id,quantity);
        System.out.println("running update cart item.. done");

        return "done";
    }

    @Override
    public List<CartItem> clearMyCart(String email) {
        Customer customer=customerRepo.findByEmail(email).get();
        cartItemRepo.removeAllByCustomer(customer.getCustomerId());
        List<CartItem> list=new ArrayList<>();
        return  list;
    }

}
