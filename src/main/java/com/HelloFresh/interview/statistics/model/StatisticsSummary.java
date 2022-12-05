package com.HelloFresh.interview.statistics.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StatisticsSummary {

    private Long total;

    private BigDecimal sumOfX;

    private BigDecimal avgOfX;

    private BigDecimal sumOfY;

    private BigDecimal avgOfY;

    public StatisticsSummary(Statistics statistics) {
        this.total = statistics.getTotal();
        this.sumOfX = statistics.getSumOfX();
        this.avgOfX = statistics.getAvgOfX();
        this.sumOfY = statistics.getSumOfY();
        this.avgOfY = statistics.getAvgOfY();
    }

    public StatisticsSummary() {
        this.total = 0L;
        this.sumOfX = BigDecimal.ZERO;
        this.avgOfX = BigDecimal.ZERO;
        this.sumOfY = BigDecimal.ZERO;
        this.avgOfY = BigDecimal.ZERO;
    }
}
