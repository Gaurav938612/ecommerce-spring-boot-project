package com.example.ecommerce.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;



    @ManyToOne
    @JoinColumn(name = "shipment_address_id")
    private Address shipmentAddress;

    @Column(name = "status")
    private String status;


    @Column(name = "payment_type")
    private String paymentType;


    @OneToOne
    @JoinColumn(name = "payment_id")
    private PaymentDetails paymentDetails;

    @Column(name="total_amount")
    private float totalAmount;
    @Column(name = "order_date")
    private Date date;

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", customer=" + customer +
                ", shipmentAddress=" + shipmentAddress +
                ", status='" + status + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", paymentDetails=" + paymentDetails +
                '}';
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Address getShipmentAddress() {
        return shipmentAddress;
    }

    public void setShipmentAddress(Address shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}
