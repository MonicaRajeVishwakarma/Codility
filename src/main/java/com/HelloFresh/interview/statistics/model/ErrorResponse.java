package com.HelloFresh.interview.statistics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
ErrorResponse class holds details of error
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private Integer errorCode;
    private String message;
}
