package com.example.ecommerce.models;

import lombok.*;

import java.util.List;


@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountData {
    private Customer customer;
    private List<Address> addresses;
    private List<CartItem> cartItems;
    private List<Orders> ordersList;
}
