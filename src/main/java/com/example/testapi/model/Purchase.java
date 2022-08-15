package com.example.testapi.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="PURCHASES")
public class Purchase {

    @Id
    @Column(name = "purchase_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long purchaseId;
    private String customerName;
    private BigDecimal price;
    private String date;


    public Purchase() {}

    public Purchase(String customerName, BigDecimal price, String date) {
        this.customerName = customerName;
        this.price = price;
        this.date = date;
    }

    public Long getId() {
        return purchaseId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getCustomerName() {
        return customerName;
    }
}
