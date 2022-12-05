package com.HelloFresh.interview.statistics.model;



import lombok.Data;

import java.math.BigDecimal;

/*
Statistics class which has statistics. Using BigDecimal for x and y as it has better precision
 */
@Data
public class Statistics {

    private Long timestamp;

    private Long total;

    private BigDecimal sumOfX;

    private BigDecimal avgOfX;

    private BigDecimal sumOfY;

    private BigDecimal avgOfY;

}
