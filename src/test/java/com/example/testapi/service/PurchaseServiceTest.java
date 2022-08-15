package com.example.testapi.service;

import com.example.testapi.controller.resource.CustomerPoints;
import com.example.testapi.model.Purchase;
import com.example.testapi.repository.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    Purchase purchase1 = new Purchase("dave", new BigDecimal(51), today.toString());
    Purchase purchase2 = new Purchase("dave", new BigDecimal(101), today.toString());
    Purchase purchase3 = new Purchase("dave", new BigDecimal(51), today.minusMonths(1).toString());
    Purchase purchase4 = new Purchase("dave", new BigDecimal(101), today.minusMonths(1).toString());
    Purchase purchase5 = new Purchase("mary", new BigDecimal(52), today.toString());
    Purchase purchase6 = new Purchase("mary", new BigDecimal(102), today.toString());
    Purchase purchase7 = new Purchase("mary", new BigDecimal(52), today.minusMonths(2).toString());
    Purchase purchase8 = new Purchase("mary", new BigDecimal(102), today.minusMonths(2).toString());
    List<Purchase> repoResponse = Arrays.asList(purchase1, purchase2, purchase3, purchase4, purchase5, purchase6, purchase7, purchase8);

    @BeforeEach
    protected void setup() {purchaseService = new PurchaseService(purchaseRepository);}

    @Test
    void getAllCustomerPoints_recordsArePresent_returnMultipleCustomers() {
        when(purchaseRepository.findLastThreeMonths(anyString())).thenReturn(repoResponse);

        List<CustomerPoints> actualResponse = purchaseService.getAllCustomerPoints();

        assertEquals(2, actualResponse.size());
    }

    @Test
    void getAllCustomerPoints_recordsArePresent_returnCorrectPointTotals() {
        when(purchaseRepository.findLastThreeMonths(anyString())).thenReturn(repoResponse);

        List<CustomerPoints> actualResponse = purchaseService.getAllCustomerPoints();

        assertEquals(106, actualResponse.get(0).getTotalPoints());
        assertEquals(112, actualResponse.get(1).getTotalPoints());
    }


    @Test
    void getAllCustomerPoints_whenRepoResponseEmpty_ReturnEmptyCustomerPoints() {
        List<Purchase> repoResponse = new ArrayList<>();

        when(purchaseRepository.findLastThreeMonths(anyString())).thenReturn(repoResponse);

        List<CustomerPoints> actualResponse = purchaseService.getAllCustomerPoints();
        List<CustomerPoints> expectedResponse = new ArrayList();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getAllCustomerPoints_whenRepoResponseIsNull_ReturnEmptyCustomerPoints() {
        when(purchaseRepository.findLastThreeMonths(anyString())).thenReturn(null);

        List<CustomerPoints> actualResponse = purchaseService.getAllCustomerPoints();
        List<CustomerPoints> expectedResponse = new ArrayList();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getAllCustomerPoints_whenBelowSinglePointThreshold_ReturnZero() {
        List<Purchase> repoResponse = new ArrayList<>();
        BigDecimal price = new BigDecimal(50);
        Purchase purchase = new Purchase("dave", price, today.toString());
        repoResponse.add(purchase);

        when(purchaseRepository.findLastThreeMonths(anyString())).thenReturn(repoResponse);

        List<CustomerPoints> actualResponse = purchaseService.getAllCustomerPoints();

        assertEquals(0, actualResponse.get(0).getTotalPoints());
    }

    @Test
    void getAllCustomerPoints_whenAboveSinglePointThreshold_ReturnPoints() {
        List<Purchase> repoResponse = new ArrayList<>();
        BigDecimal price = new BigDecimal(51);
        Purchase purchase = new Purchase("dave", price, today.toString());
        repoResponse.add(purchase);

        when(purchaseRepository.findLastThreeMonths(anyString())).thenReturn(repoResponse);

        List<CustomerPoints> actualResponse = purchaseService.getAllCustomerPoints();

        assertEquals(1, actualResponse.get(0).getTotalPoints());
    }

    @Test
    void getAllCustomerPoints_whenAboveDoublePointThreshold_ReturnPoints() {
        List<Purchase> repoResponse = new ArrayList<>();
        BigDecimal price = new BigDecimal(101);
        Purchase purchase = new Purchase("dave", price, today.toString());
        repoResponse.add(purchase);

        when(purchaseRepository.findLastThreeMonths(anyString())).thenReturn(repoResponse);

        List<CustomerPoints> actualResponse = purchaseService.getAllCustomerPoints();

        assertEquals(52, actualResponse.get(0).getTotalPoints());
    }

    @Test
    void getAllCustomerPoints_multiplePurchases_addUpPoints() {
        Purchase purchase1 = new Purchase("dave", new BigDecimal(75), today.toString());
        Purchase purchase2 = new Purchase("dave", new BigDecimal(101), today.minusDays(1).toString());
        Purchase purchase3 = new Purchase("dave", new BigDecimal(200), today.minusDays(2).toString());
        List<Purchase> repoResponse = Arrays.asList(purchase1, purchase2, purchase3);

        when(purchaseRepository.findLastThreeMonths(anyString())).thenReturn(repoResponse);

        List<CustomerPoints> actualResponse = purchaseService.getAllCustomerPoints();

        assertEquals(327, actualResponse.get(0).getTotalPoints());
    }

    @Test
    void getCustomerPoints_multipleMonths_addUpPointsPerMonth() {
        Purchase purchase1 = new Purchase("dave", new BigDecimal(75), today.toString());
        Purchase purchase2 = new Purchase("dave", new BigDecimal(101), today.minusDays(1).toString());
        Purchase purchase3 = new Purchase("dave", new BigDecimal(200), today.minusDays(2).toString());
        Purchase purchase4 = new Purchase("dave", new BigDecimal(76), today.minusMonths(1).toString());
        Purchase purchase5 = new Purchase("dave", new BigDecimal(102), today.minusMonths(1).toString());
        Purchase purchase6 = new Purchase("dave", new BigDecimal(99), today.minusMonths(1).toString());
        List<Purchase> repoResponse = Arrays.asList(purchase1, purchase2, purchase3, purchase4, purchase5, purchase6);

        when(purchaseRepository.findLastThreeMonths(anyString())).thenReturn(repoResponse);

        List<CustomerPoints> actualResponse = purchaseService.getAllCustomerPoints();

        assertEquals(327, actualResponse.get(0).getPointsByMonth().get(0).getPoints());
        assertEquals(129, actualResponse.get(0).getPointsByMonth().get(1).getPoints());
    }

    @Test
    void getCustomerPoints_multipleMonths_PointsTotal() {
        Purchase purchase1 = new Purchase("dave", new BigDecimal(75), today.toString());
        Purchase purchase2 = new Purchase("dave", new BigDecimal(101), today.minusDays(1).toString());
        Purchase purchase3 = new Purchase("dave", new BigDecimal(200), today.minusDays(2).toString());
        Purchase purchase4 = new Purchase("dave", new BigDecimal(76), today.minusMonths(1).toString());
        Purchase purchase5 = new Purchase("dave", new BigDecimal(102), today.minusMonths(1).toString());
        Purchase purchase6 = new Purchase("dave", new BigDecimal(99), today.minusMonths(1).toString());
        List<Purchase> repoResponse = Arrays.asList(purchase1, purchase2, purchase3, purchase4, purchase5, purchase6);

        when(purchaseRepository.findLastThreeMonths(anyString())).thenReturn(repoResponse);

        List<CustomerPoints> actualResponse = purchaseService.getAllCustomerPoints();

        assertEquals(456, actualResponse.get(0).getTotalPoints());
    }

}
