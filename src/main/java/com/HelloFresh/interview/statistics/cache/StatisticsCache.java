package com.HelloFresh.interview.statistics.cache;

import com.HelloFresh.interview.statistics.model.Statistics;
import com.HelloFresh.interview.statistics.utils.Constants;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class StatisticsCache {

    @Getter
    private final Map<Integer, Statistics> statisticsMap = new ConcurrentHashMap<>(Constants.LAST_INTERVAL_IN_SECONDS);
}
