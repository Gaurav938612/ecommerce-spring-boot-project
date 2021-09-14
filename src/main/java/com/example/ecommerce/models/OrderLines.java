package com.example.ecommerce.models;

import javax.persistence.*;

@Entity
@Table(name = "order_lines")
public class OrderLines {
    @Override
    public String toString() {
        return "OrderLines{" +
                "orderLines=" + orderLinesId +
                ", product=" + product +
                ", orders=" + orders +
                ", quantity=" + quantity +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_lines_id")
    private int orderLinesId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "subtotal_amount")
    private float subtotalAmount;

    public float getSubtotalAmount() {
        return subtotalAmount;
    }

    public void setSubtotalAmount(float subtotalAmount) {
        this.subtotalAmount = subtotalAmount;
    }

    public int getOrderLinesId() {
        return orderLinesId;
    }

    public void setOrderLinesId(int orderLinesId) {
        this.orderLinesId = orderLinesId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
