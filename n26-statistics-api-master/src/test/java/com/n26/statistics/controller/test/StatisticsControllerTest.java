/**
 * Copyright 2018 N26 Bank coding challenge. All rights reserved.
 */

package com.n26.statistics.controller.test;

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
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Chetankumar Hiremath
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsControllerTest extends RestCommonControllerHelper {
    // ***************************************************************************************************************
    // ******************************************** Public Fields ****************************************************
    // ***************************************************************************************************************

    TransactionServiceImpl serviceImpl = new TransactionServiceImpl();

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
        serviceImpl.getStatisticsForLastMinute().clear();
    }

    @Test
    public void testGetComputedStatisticsWithSuccessResponse() throws Exception {
        serviceImpl.getStatisticsForLastMinute().clear();
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

        getComputedStatistics().andExpect(status().isOk());
    }

    // ***************************************************************************************************************
    // ****************************************** Non Public Methods *************************************************
    // ***************************************************************************************************************

    private ResultActions getComputedStatistics() throws Exception {
        return getMethod("/statistics");
    }
}
