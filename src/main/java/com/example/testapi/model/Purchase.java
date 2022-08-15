package com.example.testapi.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="PURCHASES")
public class Purchase {

    @Id
    @Column(name = "purchase_id")
    private Long purchaseId;
    private String customerName;
    private BigDecimal price;
    private String date;


    public Purchase() {}

    public Purchase(Long purchaseId, String customerName, BigDecimal price, String date) {
        this.purchaseId = purchaseId;
        this.customerName = customerName;
        this.price = price;
        this.date = date;
    }

    public Long getId() {
        return purchaseId;
    }

    public void setId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
