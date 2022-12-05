package com.HelloFresh.interview.statistics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/*
InputDataSet class which holds arbitrary input data
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Events {

    private Long timestamp;

    private BigDecimal x;

    private BigDecimal y;

}
