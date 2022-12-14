package com.example.testapi.service;

import com.example.testapi.controller.resource.CustomerPoints;
import com.example.testapi.model.MonthPoints;
import com.example.testapi.model.Purchase;
import com.example.testapi.repository.PurchaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    String currentDateMinus3months = LocalDateTime.now().minusMonths(3).toString();

    PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public List<CustomerPoints> getAllCustomerPoints() {
        List<Purchase> purchases = purchaseRepository.findLastThreeMonths(currentDateMinus3months);

        List<CustomerPoints> response = new ArrayList<>();

        if (CollectionUtils.isEmpty(purchases)) {
            return response;
        }

        Map<String, ArrayList<Purchase>> purchaseMap = new HashMap<>();

        purchases.forEach(purchase -> {
            String name = purchase.getCustomerName();
            if (purchaseMap.containsKey(name)) {
                purchaseMap.get(name).add(purchase);
            }
            else {
                purchaseMap.put(name, new ArrayList<>());
                purchaseMap.get(name).add(purchase);
            }
        });



        for (var entry : purchaseMap.entrySet()) {
            response.add(getSingleCustomerPoints(entry.getValue(), entry.getKey()));
        }

        return response;
    };

    private CustomerPoints getSingleCustomerPoints(List<Purchase> purchases, String name) {

        Map<String, Integer> monthPointsMap = new HashMap<>();
        AtomicInteger totalPoints = new AtomicInteger();

        purchases.forEach(purchase -> {

            String month = LocalDate.parse(purchase.getDate()).getMonth().toString();
            int newPoints = getPoints(purchase.getPrice());
            totalPoints.addAndGet(newPoints);

            if (monthPointsMap.containsKey(month)) {
                monthPointsMap.put(month, monthPointsMap.get(month) + newPoints);
            }

            else {
                monthPointsMap.put(month, newPoints);
            }
        });

        List<MonthPoints> monthPoints = new ArrayList<>();
        monthPointsMap.forEach((key, value) -> {
            monthPoints.add(MonthPoints.builder().month(key).points(value).build());
        });

        return CustomerPoints.builder()
                .name(name)
                .totalPoints(totalPoints.intValue())
                .pointsByMonth(monthPoints)
                .build();
    }

    private int getPoints(BigDecimal moneySpent) {
        final int single_point_threshold = 50;
        final int double_point_threshold = 100;
        final int double_point_multiplier = 2;
        int points = 0;

        if (Objects.isNull(moneySpent)) {
            return points;
        }

        moneySpent = moneySpent.setScale(0, RoundingMode.UP);
        int moneyRounded = moneySpent.intValue();

        if (moneyRounded > double_point_threshold) {
            points = single_point_threshold + ((moneyRounded-double_point_threshold)*double_point_multiplier);
        } else if (moneyRounded > single_point_threshold) {
            points = moneyRounded - single_point_threshold;
        }

        return points;
    }
}
