package com.HelloFresh.interview.statistics.controller;

import com.HelloFresh.interview.statistics.ExpectedResults.DefaultStatisticsCache;
import com.HelloFresh.interview.statistics.StatisticsApplication;
import com.HelloFresh.interview.statistics.model.Events;
import com.HelloFresh.interview.statistics.model.StatisticsSummary;
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
import java.text.DecimalFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StatisticsApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StatisticsControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void test1_contextLoads() {
    }

    @Test
    public void test2_getStatistics_shouldReturnDefaultResponse() {

        ResponseEntity<String> response =
                testRestTemplate.getForEntity(DefaultStatisticsCache.base_url + "/stats",
                        String.class);
        assertEquals(DefaultStatisticsCache.getDefaultStatisticsSummary(), response.getBody());
    }

    @Test
    public void test3_getStatistics_shouldReturnOkStatus() {

        ResponseEntity<?> response =
                testRestTemplate.getForEntity(DefaultStatisticsCache.base_url + "/stats",
                        String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void test4_getStatistics_shouldReturnProperResponseForOneEvent() {

        Long currentTimestamp = System.currentTimeMillis();
        BigDecimal x = new BigDecimal(0.1234567891);
        BigDecimal y = new BigDecimal(1234567891);
        this.createEvent(currentTimestamp,x,y);
        String expectedStatisticsSummary = DefaultStatisticsCache.getExpectedStatisticsSummary(1L,x,y);
        ResponseEntity<?> response = testRestTemplate
                .getForEntity(DefaultStatisticsCache.base_url
                        + "/stats", String.class);
        assertEquals(expectedStatisticsSummary, response.getBody());

    }

    @Test
    public void test5_getStatistics_shouldReturnSameResponseAfterAddingEventWithOldTimestamp() {


        Long oldTimestamp = System.currentTimeMillis() - 80000;
        BigDecimal x = new BigDecimal(0.1234567891);
        BigDecimal y = new BigDecimal(1234567891);
        this.createEvent(oldTimestamp,x,y);
        String expectedStatisticsSummary =
                DefaultStatisticsCache.getExpectedStatisticsSummary(1L,x,y);

        ResponseEntity<?> response = testRestTemplate
                .getForEntity(DefaultStatisticsCache.base_url
                        + "/stats", String.class);

        assertEquals(expectedStatisticsSummary, response.getBody());

    }

    private void createEvent(Long timestamp,BigDecimal x, BigDecimal y) {

        HttpEntity<Events> request = new HttpEntity<>(new Events(timestamp,x,y));
        ResponseEntity<String> response = testRestTemplate
                .exchange(DefaultStatisticsCache.base_url
                        + "/event", HttpMethod.POST, request, String.class);
    }
}