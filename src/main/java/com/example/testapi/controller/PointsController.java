package com.example.testapi.controller;

import com.example.testapi.controller.resource.CustomerPoints;
import com.example.testapi.service.PurchaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PointsController {

    private final PurchaseService purchaseService;

    PointsController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/points")
    List<CustomerPoints> all() {
        return purchaseService.getAllCustomerPoints();
    }
}
