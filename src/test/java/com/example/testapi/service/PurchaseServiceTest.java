package com.example.testapi.service;

import com.example.testapi.controller.resource.CustomerPoints;
import com.example.testapi.model.Purchase;
import com.example.testapi.repository.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PurchaseServiceTest {

    PurchaseService purchaseService;
    final PurchaseRepository purchaseRepository = mock(PurchaseRepository.class);
    LocalDate today = LocalDate.now();

    @BeforeEach
    protected void setup() {purchaseService = new PurchaseService(purchaseRepository);}

    @Test
    void getAllCustomers_recordsArePresent_returnMultipleCustomers() {
        Purchase purchase1 = new Purchase(1L, "dave", new BigDecimal(75), today.toString());
        Purchase purchase2 = new Purchase(2L, "mary", new BigDecimal(101), today.toString());
        List<Purchase> repoResponse = Arrays.asList(purchase1, purchase2);

        when(purchaseRepository.findLastThreeMonths(anyString())).thenReturn(repoResponse);

        List<CustomerPoints> actualResponse = purchaseService.findAll();

        assertEquals(2, actualResponse.size());
    }

    @Test
    void getCustomerPoints_whenBelowSinglePointThreshold_ReturnZero() {
        List<Purchase> repoResponse = new ArrayList<>();
        BigDecimal price = new BigDecimal(50);

        Purchase purchase = new Purchase(1L, "dave", price, today.toString());
        repoResponse.add(purchase);

        when(purchaseRepository.findByCustomerName(anyString(), anyString())).thenReturn(repoResponse);

        CustomerPoints response = purchaseService.getCustomerPoints("dave");
        assertEquals("dave", response.getName());
        assertEquals(0, response.getPointsByMonth().get(0).getPoints());
    }

    @Test
    void getCustomerPoints_whenAboveSinglePointThreshold_ReturnPoints() {
        List<Purchase> repoResponse = new ArrayList<>();
        BigDecimal price = new BigDecimal(51);
        Purchase purchase = new Purchase(1L, "dave", price, today.toString());
        repoResponse.add(purchase);

        when(purchaseRepository.findByCustomerName(anyString(), anyString())).thenReturn(repoResponse);

        CustomerPoints response = purchaseService.getCustomerPoints("dave");
        assertEquals(1, response.getPointsByMonth().get(0).getPoints());
    }

    @Test
    void getCustomerPoints_whenAboveDoublePointThreshold_ReturnPoints() {
        List<Purchase> repoResponse = new ArrayList<>();
        BigDecimal price = new BigDecimal(101);
        Purchase purchase = new Purchase(1L, "dave", price, today.toString());
        repoResponse.add(purchase);

        when(purchaseRepository.findByCustomerName(anyString(), anyString())).thenReturn(repoResponse);

        CustomerPoints response = purchaseService.getCustomerPoints("dave");
        assertEquals(52, response.getPointsByMonth().get(0).getPoints());
    }

    @Test
    void getCustomerPoints_multiplePurchases_addUpPoints() {
        Purchase purchase1 = new Purchase(1L, "dave", new BigDecimal(75), today.toString());
        Purchase purchase2 = new Purchase(2L, "dave", new BigDecimal(101), today.minusDays(1).toString());
        Purchase purchase3 = new Purchase(3L, "dave", new BigDecimal(200), today.minusDays(2).toString());
        List<Purchase> repoResponse = Arrays.asList(purchase1, purchase2, purchase3);

        when(purchaseRepository.findByCustomerName(anyString(), anyString())).thenReturn(repoResponse);

        CustomerPoints response = purchaseService.getCustomerPoints("dave");
        assertEquals(327, response.getPointsByMonth().get(0).getPoints());
    }

    @Test
    void getCustomerPoints_multipleMonths_addUpPointsPerMonth() {
        Purchase purchase1 = new Purchase(1L, "dave", new BigDecimal(75), today.toString());
        Purchase purchase2 = new Purchase(2L, "dave", new BigDecimal(101), today.minusDays(1).toString());
        Purchase purchase3 = new Purchase(3L, "dave", new BigDecimal(200), today.minusDays(2).toString());
        Purchase purchase4 = new Purchase(4L, "dave", new BigDecimal(76), today.minusMonths(1).toString());
        Purchase purchase5 = new Purchase(5L, "dave", new BigDecimal(102), today.minusMonths(1).toString());
        Purchase purchase6 = new Purchase(6L, "dave", new BigDecimal(99), today.minusMonths(1).toString());
        List<Purchase> repoResponse = Arrays.asList(purchase1, purchase2, purchase3, purchase4, purchase5, purchase6);

        when(purchaseRepository.findByCustomerName(anyString(), anyString())).thenReturn(repoResponse);

        CustomerPoints response = purchaseService.getCustomerPoints("dave");
        assertEquals(327, response.getPointsByMonth().get(0).getPoints());
        assertEquals(129, response.getPointsByMonth().get(1).getPoints());
    }

    @Test
    void getCustomerPoints_multipleMonths_PointsTotal() {
        Purchase purchase1 = new Purchase(1L, "dave", new BigDecimal(75), today.toString());
        Purchase purchase2 = new Purchase(2L, "dave", new BigDecimal(101), today.minusDays(1).toString());
        Purchase purchase3 = new Purchase(3L, "dave", new BigDecimal(200), today.minusDays(2).toString());
        Purchase purchase4 = new Purchase(4L, "dave", new BigDecimal(76), today.minusMonths(1).toString());
        Purchase purchase5 = new Purchase(5L, "dave", new BigDecimal(102), today.minusMonths(1).toString());
        Purchase purchase6 = new Purchase(6L, "dave", new BigDecimal(99), today.minusMonths(1).toString());
        List<Purchase> repoResponse = Arrays.asList(purchase1, purchase2, purchase3, purchase4, purchase5, purchase6);

        when(purchaseRepository.findByCustomerName(anyString(), anyString())).thenReturn(repoResponse);

        CustomerPoints response = purchaseService.getCustomerPoints("dave");
        assertEquals(456, response.getTotalPoints());
    }

}
