package guru.springframework.brewery.web.controllers;

import static org.assertj.core.api.Java6Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import guru.springframework.brewery.domain.Customer;
import guru.springframework.brewery.repositories.CustomerRepository;
import guru.springframework.brewery.web.model.BeerOrderPagedList;

/**
 * Created by jt on 2019-03-12.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerOrderControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    CustomerRepository customerRepository;

    Customer customer;

    @BeforeEach
    void setUp() {
        customer = customerRepository.findAll().get(0);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testListOrders() {
        String url = "/api/v1/customers/" + customer.getId().toString() + " /orders";

        BeerOrderPagedList pagedList = testRestTemplate.getForObject(url, BeerOrderPagedList.class);
        
        assertThat(pagedList.getContent()).hasSize(1);
    }
}
