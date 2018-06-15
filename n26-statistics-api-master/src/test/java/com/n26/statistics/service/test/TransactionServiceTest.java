/**
 * Copyright 2018 N26 Bank coding challenge. All rights reserved.
 */

package com.n26.statistics.service.test;

import com.n26.statistics.exception.InvalidTransactionException;
import com.n26.statistics.exception.ValidationException;
import com.n26.statistics.model.StatisticsData;
import com.n26.statistics.model.Transaction;
import com.n26.statistics.service.TransactionService;
import com.n26.statistics.service.impl.TransactionServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author Chetankumar Hiremath
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {
    // ***************************************************************************************************************
    // ******************************************** Public Fields ****************************************************
    // ***************************************************************************************************************

    // ***************************************************************************************************************
    // ****************************************** Non Public Fields **************************************************
    // ***************************************************************************************************************

    @Autowired
    private TransactionService transactionService;

    // ***************************************************************************************************************
    // *********************************************** Constructors **************************************************
    // ***************************************************************************************************************

    // ***************************************************************************************************************
    // ******************************************** Public Methods ***************************************************
    // ***************************************************************************************************************

    @Before
    public void setUp() {
        final TransactionServiceImpl serviceImpl = new TransactionServiceImpl();
        serviceImpl.getStatisticsForLastMinute().clear();
    }

    @Test(expected = InvalidTransactionException.class)
    public void testInvalidTimeStampWithException() {
        transactionService.addTransaction(new Transaction(15.5, System.currentTimeMillis() - 70000));
    }

    @Test(expected = ValidationException.class)
    public void testForEmptyRequestBodyToThrowException() {
        transactionService.addTransaction(null);
    }

    @Test(expected = ValidationException.class)
    public void testWithMissingAmountFieldToThrowException() {
        transactionService.addTransaction(new Transaction(null, System.currentTimeMillis()));
    }

    @Test(expected = ValidationException.class)
    public void testWithMissingTimeStampFieldToThrowException() {
        transactionService.addTransaction(new Transaction(20.2, null));
    }

    @Test(expected = ValidationException.class)
    public void testWithMissingAmountAndTimeStampFieldToThrowException() {
        transactionService.addTransaction(new Transaction(null, null));
    }

    @Test
    public void testWithValidDataForSuccessResponse() {
        transactionService.addTransaction(new Transaction(8.5, System.currentTimeMillis() - 10000));
        transactionService.addTransaction(new Transaction(15.5, System.currentTimeMillis() - 7000));
        transactionService.addTransaction(new Transaction(20.2, System.currentTimeMillis() - 6500));
        transactionService.addTransaction(new Transaction(5.8, System.currentTimeMillis() - 4300));
        transactionService.addTransaction(new Transaction(3.6, System.currentTimeMillis() - 8500));
        transactionService.addTransaction(new Transaction(2.4, System.currentTimeMillis() - 2500));
        transactionService.addTransaction(new Transaction(9.5, System.currentTimeMillis() - 5500));
        transactionService.addTransaction(new Transaction(12.5, System.currentTimeMillis() - 3000));

        final StatisticsData statisticsData = transactionService.computeStatistics();
        assertEquals(statisticsData.getSum(), 78, 0.0);
        assertEquals(statisticsData.getMax(), 20.2, 0.0);
        assertEquals(statisticsData.getMin(), 2.4, 0.0);
        assertEquals(statisticsData.getAvg(), 78 / 8.0, 0.00);
        assertEquals(statisticsData.getCount(), 8l);
    }

    // ***************************************************************************************************************
    // ****************************************** Non Public Methods *************************************************
    // ***************************************************************************************************************
}
