package com.example.testapi.controller;

import com.example.testapi.controller.resource.CustomerPoints;
import com.example.testapi.model.Customer;
import com.example.testapi.model.Purchase;
import com.example.testapi.service.PurchaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    private final PurchaseService purchaseService;

    CustomerController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/customers")
    List<CustomerPoints> all() {
        return purchaseService.findAll();
    }

    @GetMapping("/customers/{name}/points")
    CustomerPoints getPoints(@PathVariable String name) {
        return purchaseService.getCustomerPoints(name);
    }
}
