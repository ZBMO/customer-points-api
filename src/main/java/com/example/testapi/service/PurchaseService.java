package com.example.testapi.service;

import com.example.testapi.controller.resource.CustomerPoints;
import com.example.testapi.model.MonthPoints;
import com.example.testapi.model.Purchase;
import com.example.testapi.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    LocalDateTime currentDateTime = LocalDateTime.now();
    String currentDateMinus3months = currentDateTime.minusMonths(3).toString();

    PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }
    public CustomerPoints getCustomerPoints(String name) {
        List<Purchase> purchases = purchaseRepository.findByCustomerName(name, currentDateMinus3months);

        Map<String, Integer> monthPointsMap = new HashMap<>();
        AtomicInteger totalPoints = new AtomicInteger();

        purchases.forEach(purchase -> {
            String month = LocalDateTime.from(DateTimeFormatter.ISO_INSTANT.parse(purchase.getDate())).getMonth().toString();
            int newPoints = getPoints(purchase.getPrice());
            totalPoints.addAndGet(newPoints);

            if (monthPointsMap.containsKey(month)) {
                monthPointsMap.put(month, monthPointsMap.get(month) + newPoints);
            }

            if (!monthPointsMap.containsKey(month)) {
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
