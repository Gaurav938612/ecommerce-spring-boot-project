package com.example.ecommerce;

import com.example.ecommerce.models.CartItem;
import com.example.ecommerce.models.Customer;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.repository.CartItemRepo;
import com.example.ecommerce.repository.CustomerRepo;
import com.example.ecommerce.repository.ProductRepo;
import com.example.ecommerce.services.CartService;
import com.example.ecommerce.services.CartServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

public class CartServiceTest {
    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private ProductRepo productRepo;
    @Mock
    private CartItemRepo cartItemRepo;

    private CartService cartService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        cartService=new CartServiceImpl(customerRepo,productRepo,cartItemRepo);
        Customer customer=new Customer("first_name","last_name",
                "email","password","ph_no","USER");
        Product product=new Product();
        product.setProductId(1);
        product.setInventory(2);

        CartItem cartItem=new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(10);

        Mockito.doReturn(customer).when(customerRepo).getById(any());
        Mockito.doReturn(Optional.of(customer)).when(customerRepo).findByEmail(any());
        Mockito.doReturn(product).when(productRepo).getById(any());
        Mockito.doReturn(cartItem).when(cartItemRepo).save(any());

        Mockito.doReturn(Arrays.asList(cartItem)).when(cartItemRepo).findAllByCustomer(customer);

        //manage cart item quantity

        Mockito.doReturn(Optional.of(product)).when(productRepo).findById(any());
        Mockito.doReturn(Optional.of(customer)).when(customerRepo).findById(any());
        Mockito.doReturn(1).when(productRepo).updateProductItemQuantity(anyInt(),anyInt());
        Mockito.doNothing().when(cartItemRepo).updateCartItem(anyInt(),anyInt(),anyInt());


    }

    @Test
    public void addToCartTest() throws SQLException {
        String act=cartService.addToCart(1,1);
        Assertions.assertEquals("added to cart",act);
    }

    @Test
    public void getCartItemTest() throws SQLException {
        List<CartItem> myCarts=cartService.getMyCarts("abc@gmail.com");
        int cart_item_quantity=10;
        Assertions.assertEquals(cart_item_quantity,myCarts.get(0).getQuantity());
    }

    @Test
    public void manageCartItemTest() throws SQLException {
        String res=cartService.manageCartItem(1,1,6);
        String res2=cartService.manageCartItem(1,1,2);
        Assertions.assertEquals("not enough in inventory",res);
        Assertions.assertEquals("done",res2);
    }


}
