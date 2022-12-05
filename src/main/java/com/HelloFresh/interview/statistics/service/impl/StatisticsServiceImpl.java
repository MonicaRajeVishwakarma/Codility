package com.HelloFresh.interview.statistics.service.impl;

import com.HelloFresh.interview.statistics.cache.StatisticsCache;
import com.HelloFresh.interview.statistics.model.Events;
import com.HelloFresh.interview.statistics.model.Statistics;
import com.HelloFresh.interview.statistics.model.StatisticsSummary;
import com.HelloFresh.interview.statistics.service.StatisticsService;
import com.HelloFresh.interview.statistics.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Slf4j
@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsCache statisticsCache;

    @Autowired
    public StatisticsServiceImpl(StatisticsCache statisticsCache){
        this.statisticsCache = statisticsCache;
    }

    @Override
    public String get() {
        long currTime = System.currentTimeMillis();
        StatisticsSummary statisticsSummary = statisticsCache.getStatisticsMap().values().
                stream().filter(s -> (currTime - s.getTimestamp())/1000  < Constants.LAST_INTERVAL_IN_SECONDS)
                .map(StatisticsSummary::new)
                .reduce(new StatisticsSummary(), (s1,s2) ->{
                    s1.setSumOfX(s1.getSumOfX().add(s2.getSumOfX()));
                    s1.setSumOfY(s1.getSumOfY().add(s2.getSumOfY()));
                    s1.setTotal(s1.getTotal()+s2.getTotal());
                    s1.setAvgOfX((s1.getAvgOfX().add(s2.getAvgOfX())).divide(new BigDecimal(2)));
                    s1.setAvgOfY((s1.getAvgOfY().add(s2.getAvgOfY())).divide(new BigDecimal(2)));
                    return  s1; });
        updateStatisticsSummary(statisticsSummary);
        log.debug("Statistics Service calculated statistics summary for last minute => {}", statisticsSummary);
        log.debug("Statistics Service contains in-memory statistics until now: {}, size is {}", statisticsCache.getStatisticsMap(), statisticsCache.getStatisticsMap().size());

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(10);
        df.setGroupingUsed(false);
        String stats = statisticsSummary.getTotal().toString()+","+
                df.format(statisticsSummary.getSumOfX())+","+
                df.format(statisticsSummary.getAvgOfX())+","+
                df.format(statisticsSummary.getSumOfY())+","+
                df.format(statisticsSummary.getAvgOfY());

        return stats;
    }

    private void updateStatisticsSummary(StatisticsSummary summary) {
        summary.setAvgOfX(calculateAverage(summary,"X"));
        summary.setAvgOfY(calculateAverage(summary,"Y"));
    }

    private BigDecimal calculateAverage(StatisticsSummary summary,String type){
        BigDecimal sum = type.equals("X") ? summary.getSumOfX():summary.getSumOfY();
        BigDecimal avg = summary.getTotal() > 0L ? (sum.divide(new BigDecimal(summary.getTotal()))) : BigDecimal.ZERO;
        return avg;
    }

    @Override
    public void compute(Events events, Long currTimeStamp){

        int second = LocalDateTime.ofInstant(Instant.ofEpochMilli(events.getTimestamp()), ZoneId.systemDefault())
                .getSecond();

        statisticsCache.getStatisticsMap().compute(second,(key, existingStatistics)  -> {
            if(Objects.isNull(existingStatistics) || isStatisticsUnderInterval(currTimeStamp,existingStatistics)){
                return createStatistic(events);
            }
                return updateStatistics(existingStatistics,events);
        });
    }


    private Statistics createStatistic(Events events) {
        Statistics statistics = new Statistics();
        statistics.setTimestamp(events.getTimestamp());
        statistics.setTotal(1L);
        statistics.setSumOfX(events.getX());
        statistics.setAvgOfX(events.getX());
        statistics.setSumOfY(events.getY());
        statistics.setAvgOfY(events.getY());
        return statistics;
    }

    private Statistics updateStatistics(Statistics existingStatistics,Events events){
        existingStatistics.setTotal(existingStatistics.getTotal()+1L);
        existingStatistics.setSumOfX(existingStatistics.getSumOfX().add(events.getX()));
        existingStatistics.setSumOfY(existingStatistics.getSumOfY().add(events.getY()));
        return existingStatistics;
    }

    private boolean isStatisticsUnderInterval(Long currTimeStamp, Statistics existingStatistics){
        return (currTimeStamp - existingStatistics.getTimestamp())/1000  >= Constants.LAST_INTERVAL_IN_SECONDS;
    }

}
