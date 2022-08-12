package com.example.testapi.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="CUSTOMERS")
public class Customer {

    @Id
    private Long id;
    private String name;

    public Customer(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public Customer() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
