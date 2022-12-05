package com.HelloFresh.interview.statistics.service;

import com.HelloFresh.interview.statistics.model.Events;
import com.HelloFresh.interview.statistics.model.StatisticsSummary;

/*
StatisticsService Interface for statistics service
 */
public interface StatisticsService {

     String get();

     void compute(Events events, Long currentTimestamp);
}
