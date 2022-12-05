package com.HelloFresh.interview.statistics.controller;

import com.HelloFresh.interview.statistics.ExpectedResults.DefaultStatisticsCache;
import com.HelloFresh.interview.statistics.StatisticsApplication;
import com.HelloFresh.interview.statistics.model.Events;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StatisticsApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void test1_contextLoads() {
    }

    @Test
    public void test2_addEvent_shouldReturnHttpStatusCreated() {

        HttpEntity<Events> request = new HttpEntity<>(new Events(System.currentTimeMillis(),
                new BigDecimal(0.02111212), new BigDecimal(14423122)));

        ResponseEntity<Object> response = testRestTemplate
                .exchange(DefaultStatisticsCache.base_url + "/event", HttpMethod.POST, request, Object.class);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void test3_addEvent_shouldReturnHttpStatusNoContent() {

        HttpEntity<Events> request = new HttpEntity<>(new Events(System.currentTimeMillis()-800000,
                new BigDecimal(0.02111212), new BigDecimal(14423122)));
        ResponseEntity<Object> response = testRestTemplate
                .exchange(DefaultStatisticsCache.base_url + "/event", HttpMethod.POST, request, Object.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}