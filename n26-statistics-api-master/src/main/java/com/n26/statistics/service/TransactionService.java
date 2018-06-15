/**
 * Copyright 2018 N26 Bank coding challenge. All rights reserved.
 */

package com.n26.statistics.service;

import com.n26.statistics.model.StatisticsData;
import com.n26.statistics.model.Transaction;

/**
 * @author Chetankumar Hiremath
 *
 */
public interface TransactionService {
    // ***************************************************************************************************************
    // ******************************************** Public Fields ****************************************************
    // ***************************************************************************************************************

    // ***************************************************************************************************************
    // ****************************************** Non Public Fields **************************************************
    // ***************************************************************************************************************

    // ***************************************************************************************************************
    // ******************************************** Public Methods ***************************************************
    // ***************************************************************************************************************

    // ***************************************************************************************************************
    // ****************************************** Non Public Methods *************************************************
    // ***************************************************************************************************************

    /**
     * Add a transaction by calculating the statistics for last 60 seconds when new transaction data is received.
     *
     * @return Transaction
     */
    Transaction addTransaction(Transaction transaction);

    /**
     * Calculates and returns combined statistics based on last minute.
     *
     * @return StatisticsData
     *         calculated Statistics data
     *
     */
    StatisticsData computeStatistics();

    /**
     * Delete the old transaction, if it is older than a minute
     */
    void deleteOldTransactions();
}
