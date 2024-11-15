package guru.springframework.brewery.web.controllers;

import static org.assertj.core.api.Java6Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import guru.springframework.brewery.web.model.BeerPagedList;


/**
 * Created by jt on 2019-03-03.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @SuppressWarnings("deprecation")
    void testListBeers() {
        BeerPagedList beerPagedList = restTemplate.getForObject("/api/v1/beer", BeerPagedList.class);

        assertThat(beerPagedList.getContent()).hasSize(3);
    }
}
