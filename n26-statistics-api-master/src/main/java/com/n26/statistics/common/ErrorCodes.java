/**
 * Copyright 2018 N26 Bank coding challenge. All rights reserved.
 */

package com.n26.statistics.common;

/**
 * @author Chetankumar Hiremath
 *
 */
public enum ErrorCodes {
    INVALID_TRANSACTION(1111, "Transaction is older than 60 seconds"),
    VALIDATE_EMPTY_REQUEST_BODY(2222, "Empty request body"),
    VALIDATE_MISSING_TIMESTAMP(3333, "Missing the required timestamp field"),
    VALIDATE_MISSING_AMOUNT(4444, "Missing the required amount field"),
    VALIDATE_MISSING_AMOUNT_AND_TIMESTAMP(5555, "Missing the required amount and timestamp field"),
    NO_STATISTICS_FOUND(9999, "No statistics found for last 60 secs");

    private Integer code;

    private String errorMsg;

    ErrorCodes(Integer code, String message) {
        this.code = code;
        this.errorMsg = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.errorMsg;
    }
}
