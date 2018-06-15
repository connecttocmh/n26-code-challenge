/**
 * Copyright 2018 N26 Bank coding challenge. All rights reserved.
 */

package com.n26.statistics.exception;

import com.n26.statistics.common.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * N26ExceptionHandler class returns meaningful error responses to the end user, whenever any exception is thrown.
 *
 * @author Chetankumar Hiremath
 *
 */
@Slf4j
@ControllerAdvice
public class N26ExceptionHandler {
    // ***************************************************************************************************************
    // ******************************************** Public Fields ****************************************************
    // ***************************************************************************************************************

    // ***************************************************************************************************************
    // ****************************************** Non Public Fields **************************************************
    // ***************************************************************************************************************
    private static final String EXCEPTION_MESSAGE = "Exception occured due to, error code: {}; exception message: {}";
    // ***************************************************************************************************************
    // *********************************************** Constructors **************************************************
    // ***************************************************************************************************************

    // ***************************************************************************************************************
    // ******************************************** Public Methods ***************************************************
    // ***************************************************************************************************************

    /**
     * Handles validation exception
     *
     * @param validationException ValidationException
     * @return ErrorResponse
     */
    @ResponseBody
    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(ValidationException validationException) {
        log.error(EXCEPTION_MESSAGE, validationException.getErrorCode(), validationException.getErrorMessage());
        return new ErrorResponse(validationException.getErrorCode(), validationException.getErrorMessage());
    }

    /**
     * Handles StatisticsNotFoundException
     *
     * @param notFoundException StatisticsNotFoundException
     * @return ErrorResponse
     */
    @ResponseBody
    @ExceptionHandler(value = StatisticsNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorResponse handleException(StatisticsNotFoundException notFoundException) {
        log.error(EXCEPTION_MESSAGE, notFoundException.getErrorCode(), notFoundException.getErrorMessage());
        return new ErrorResponse(notFoundException.getErrorCode(), notFoundException.getErrorMessage());
    }

    /**
     * Handles InvalidTransactionException
     *
     * @param invalidTransactionException InvalidTransactionException
     * @return ErrorResponse
     */
    @ResponseBody
    @ExceptionHandler(value = InvalidTransactionException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorResponse handleException(InvalidTransactionException invalidTransactionException) {
        log.error(EXCEPTION_MESSAGE, invalidTransactionException.getErrorCode(), invalidTransactionException.getErrorMessage());
        return new ErrorResponse(invalidTransactionException.getErrorCode(), invalidTransactionException.getErrorMessage());
    }

    // ***************************************************************************************************************
    // ****************************************** Non Public Methods *************************************************
    // ***************************************************************************************************************
}
