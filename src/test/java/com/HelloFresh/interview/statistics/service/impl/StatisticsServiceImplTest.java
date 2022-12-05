package com.HelloFresh.interview.statistics.service.impl;

import com.HelloFresh.interview.statistics.ExpectedResults.DefaultStatisticsCache;
import com.HelloFresh.interview.statistics.cache.StatisticsCache;
import com.HelloFresh.interview.statistics.model.Events;
import com.HelloFresh.interview.statistics.model.StatisticsSummary;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class StatisticsServiceImplTest {


    private  StatisticsCache statisticsCache;

    private  StatisticsServiceImpl statisticsServiceTest;


    @Before
    public void setUp() {
        statisticsCache = new StatisticsCache();
        //log.info("statisticsCache in setUp"+statisticsCache);
        statisticsServiceTest = new StatisticsServiceImpl(statisticsCache);
    }

    @Test
    public void get() {
        //Given When Then
        clearStatisticsCache();
        String expectedStatisticsSummary = DefaultStatisticsCache.getDefaultStatisticsSummary();
        String actualStatisticsSummary = statisticsServiceTest.get();
        assertEquals(expectedStatisticsSummary,actualStatisticsSummary);
    }

    @Test
    public void compute_to_storeNewEntry() {
        clearStatisticsCache();
        Events event = new Events(System.currentTimeMillis(), new BigDecimal(0.00111212), new BigDecimal(12323122));
        statisticsServiceTest.compute(event,System.currentTimeMillis());
        assertEquals(1,statisticsCache.getStatisticsMap().size());
    }

    @Test
    public void compute_to_StoreOnlyOneEntryForASameTimeStamp(){
        //Given When Then
        clearStatisticsCache();
        Long timeStamp = System.currentTimeMillis();
        Events event1 = new Events(timeStamp, new BigDecimal(0.00111212), new BigDecimal(12323122));
        Events event2 = new Events(timeStamp, new BigDecimal(0.02111212), new BigDecimal(14423122));
        statisticsServiceTest.compute(event1,timeStamp);
        statisticsServiceTest.compute(event2,timeStamp);
        assertEquals(1,statisticsCache.getStatisticsMap().size());

    }

    @Test
    public void compute_to_StoreDifferentCountForEntriesForDifferentTimeStamp(){
        //Given When Then
        clearStatisticsCache();
        Long timeStamp = System.currentTimeMillis();
        Events event1 = new Events(timeStamp, new BigDecimal(0.00111212), new BigDecimal(12323122));
        Events event2 = new Events(timeStamp+2000, new BigDecimal(0.02111212), new BigDecimal(14423122));
        statisticsServiceTest.compute(event1,timeStamp);
        statisticsServiceTest.compute(event2,timeStamp);
        assertEquals(2,statisticsCache.getStatisticsMap().size());

    }

    private void clearStatisticsCache(){
        statisticsCache.getStatisticsMap().clear();
    }
}