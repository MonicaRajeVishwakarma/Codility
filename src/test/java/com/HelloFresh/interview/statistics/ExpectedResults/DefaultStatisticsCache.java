package com.HelloFresh.interview.statistics.ExpectedResults;

import com.HelloFresh.interview.statistics.model.StatisticsSummary;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Slf4j
public class DefaultStatisticsCache {

    public static final String base_url = "http://localhost:8080";

    public static String getDefaultStatisticsSummary(){
        BigDecimal defaultValue = new BigDecimal(0);
        String statisticsSummary = new String();
        statisticsSummary = "0,0,0,0,0";
        return statisticsSummary;
    }

    public static String getExpectedStatisticsSummary(Long total,BigDecimal x, BigDecimal y){
        String statisticsSummary = new String();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(10);
        df.setGroupingUsed(false);
        String xstr = df.format(x);
        String ystr = df.format(y);
        statisticsSummary = total+","+xstr+","+xstr+","+ystr+","+ystr;
        return statisticsSummary;
    }
}
