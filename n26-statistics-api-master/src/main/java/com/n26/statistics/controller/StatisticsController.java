/**
 * Copyright 2018 N26 Bank coding challenge. All rights reserved.
 */

package com.n26.statistics.controller;

import com.n26.statistics.common.ErrorCodes;
import com.n26.statistics.exception.StatisticsNotFoundException;
import com.n26.statistics.model.StatisticsData;
import com.n26.statistics.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API for consuming statistics requests.
 *
 * @author Chetankumar Hiremath
 *
 */
@RestController
public class StatisticsController {
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

    /**
     * Get the computed statistics data
     *
     * @return statisticsData StatisticsData
     *         calculated statistics data
     */
    @RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public StatisticsData getStatistics() {
        final StatisticsData statisticsData = transactionService.computeStatistics();
        if (statisticsData.getCount() == 0) {
            throw new StatisticsNotFoundException(ErrorCodes.NO_STATISTICS_FOUND);
        }
        return statisticsData;
    }

    // ***************************************************************************************************************
    // ****************************************** Non Public Methods *************************************************
    // ***************************************************************************************************************
}
