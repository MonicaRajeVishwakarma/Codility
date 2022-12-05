package com.HelloFresh.interview.statistics.exception;

import lombok.Getter;

public enum ErrorCode {

    EMPTY_REQUEST_BODY_VALIDATION(5001,"Request Body is empty"),

    MISSING_TIMESTAMP(5002,"Timestamp field is missing"),

    MISSING_X_FIELD(5003,"X field is missing"),

    MISSING_Y_FIELD(5004,"Y field is missing"),

    UNEXPECTED_ERROR(5000,"Internal Server Error");

    @Getter
    private Integer code;

    @Getter
    private String message;

    ErrorCode(Integer code, String message){
        this.code = code;
        this.message = message;
    }

}
