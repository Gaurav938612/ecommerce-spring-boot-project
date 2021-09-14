package com.example.ecommerce.services;

import com.example.ecommerce.models.*;
import com.example.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private  OrderRepo orderRepo;
    private CustomerRepo customerRepo;
    private AddressRepo addressRepo;
    private OrderLInesRepo orderLInesRepo;
    private CartItemRepo cartItemRepo;
    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo,CustomerRepo customerRepo,AddressRepo addressRepo,
                            OrderLInesRepo orderLInesRepo,CartItemRepo cartItemRepo){
        this.orderRepo=orderRepo;
        this.customerRepo=customerRepo;
        this.addressRepo=addressRepo;
        this.orderLInesRepo=orderLInesRepo;
        this.cartItemRepo=cartItemRepo;

    }




    @Override
    public String orderItems(String email,int addressId,String paymentType) throws SQLException {

        Customer customer=customerRepo.findByEmail(email).get();
        Address address=addressRepo.getById(addressId);

        List<CartItem> cartItems=cartItemRepo.findAllByCustomer(customer);
        float total=0;
        for(CartItem cartItem:cartItems){
            total+=cartItem.getProduct().getPrice()*cartItem.getQuantity();
        }

        Orders orders=new Orders();
        orders.setStatus("PLACED");
        orders.setPaymentType(paymentType);
        orders.setTotalAmount(total);
        orders.setCustomer(customer);
        orders.setShipmentAddress(address);
        orders.setDate(new Date());
        Orders created_Orders=orderRepo.save(orders);
        int id=created_Orders.getOrderId();

        List<OrderLines> orderLines=new ArrayList<>();
        for(CartItem cartItem:cartItems){
            OrderLines o1=new OrderLines();
            o1.setProduct(cartItem.getProduct());
            o1.setOrders(created_Orders);
            o1.setQuantity(cartItem.getQuantity());
            o1.setSubtotalAmount(cartItem.getProduct().getPrice()*cartItem.getQuantity());
            orderLines.add(o1);
        }
        orderLInesRepo.saveAll(orderLines);
        return "done";
    }

    @Override
    public List<Orders> getMyOrders(int cust_id) throws SQLException{
        return orderRepo.findAllByCustId(cust_id);
    }

    @Override
    public void cancelOrder(int ord_id, Customer customer) throws SQLException {

        Orders order=orderRepo.findById(ord_id).get();
        if(order.getCustomer().getCustomerId()!=customer.getCustomerId()){
            throw new SecurityException("not allowed");
        }
        int res;
        if(order.getStatus().equals("SHIPPED"))
            throw new SecurityException("not allowed");
        else
            res=orderRepo.updateOrderItem(ord_id,"CANCELLED BY USER");
        if(res==0)
            throw new SQLDataException("order not found ");
    }


    @Override
    public List<Orders> viewMyOrders(int id) {

        Customer user=customerRepo.findById(id).get();
        List<Orders> orders = orderRepo.findAllByCustId(id);
        for (Orders ord:orders){
            ord.setCustomer(null);
            ord.getShipmentAddress().getCustomer().setPassword(null);
        }
        Collections.sort(orders, new Comparator<Orders>() {
            @Override
            public int compare(Orders o1, Orders o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        return orders;

    }
}
