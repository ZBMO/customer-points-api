package com.example.testapi;

import com.example.testapi.model.Customer;
import com.example.testapi.model.Purchase;
import com.example.testapi.repository.CustomerRepository;
import com.example.testapi.repository.PurchaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CustomerRepository customerRepository, PurchaseRepository purchaseRepository) {
        LocalDate today = LocalDate.now();
        return args -> {
            log.info("Preloading purchases" + purchaseRepository.save(new Purchase(1L, "dave", new BigDecimal(55), today.toString())));
            log.info("Preloading purchases" + purchaseRepository.save(new Purchase(2L, "dave", new BigDecimal(72.33), today.toString())));
            log.info("Preloading purchases" + purchaseRepository.save(new Purchase(3L, "dave", new BigDecimal(10.99), today.toString())));
            log.info("Preloading purchases" + purchaseRepository.save(new Purchase(4L, "dave", new BigDecimal(102.33), today.minusMonths(1).toString())));
            log.info("Preloading purchases" + purchaseRepository.save(new Purchase(5L, "dave", new BigDecimal(100.55), today.minusMonths(1).toString())));
            log.info("Preloading purchases" + purchaseRepository.save(new Purchase(6L, "dave", new BigDecimal(245.10), today.minusMonths(1).toString())));
            log.info("Preloading purchases" + purchaseRepository.save(new Purchase(7L, "dave", new BigDecimal(71.36), today.minusMonths(2).toString())));
            log.info("Preloading purchases" + purchaseRepository.save(new Purchase(8L, "dave", new BigDecimal(222.22), today.minusMonths(2).toString())));
            log.info("Preloading purchases" + purchaseRepository.save(new Purchase(9L, "dave", new BigDecimal(18.86), today.minusMonths(3).toString())));
        };
    }

}