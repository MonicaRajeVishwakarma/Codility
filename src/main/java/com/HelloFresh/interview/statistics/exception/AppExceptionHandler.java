package com.HelloFresh.interview.statistics.exception;

import com.HelloFresh.interview.statistics.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(ValidationException validationException){
        log.error(validationException.getErrorMessage(),validationException);
        return  new ErrorResponse(validationException.getErrorCode(),validationException.getErrorMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception exception){
        log.error(exception.getMessage(),exception);
        return  new ErrorResponse(ErrorCode.UNEXPECTED_ERROR.getCode(),ErrorCode.UNEXPECTED_ERROR.getMessage());
    }
}
