package com.example.ecommerce.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "payment_details")
public class PaymentDetails {
    @Id
    @Column(name = "payment_id")
    private int paymentId;

    @Column(name = "transaction_id")
    private int transactionId;

    @Column(name = "amount")
    private float amount;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "status")
    private String status;

}
