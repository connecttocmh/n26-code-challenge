/**
 * Copyright 2018 N26 Bank coding challenge. All rights reserved.
 */

package com.n26.statistics.service.impl;

import com.n26.statistics.common.ErrorCodes;
import com.n26.statistics.exception.InvalidTransactionException;
import com.n26.statistics.exception.ValidationException;
import com.n26.statistics.model.StatisticsData;
import com.n26.statistics.model.Transaction;
import com.n26.statistics.service.TransactionService;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

/**
 * @author Chetankumar Hiremath
 *
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    // ***************************************************************************************************************
    // ******************************************** Public Fields ****************************************************
    // ***************************************************************************************************************

    // ***************************************************************************************************************
    // ****************************************** Non Public Fields **************************************************
    // ***************************************************************************************************************

    List<Transaction> transactionsList = new ArrayList<>();

    private static final Long ONE_MINUTE_IN_MILLI_SEC = 60 * 1000l;

    // ***************************************************************************************************************
    // *********************************************** Constructors **************************************************
    // ***************************************************************************************************************

    // ***************************************************************************************************************
    // ******************************************** Public Methods ***************************************************
    // ***************************************************************************************************************

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction addTransaction(Transaction transaction) {
        final ZonedDateTime utcTimeZone = ZonedDateTime.now(ZoneOffset.UTC);
        final long epochInMillis = utcTimeZone.toEpochSecond() * 1000;

        if (transaction == null)
            throw new ValidationException(ErrorCodes.VALIDATE_EMPTY_REQUEST_BODY);

        if (transaction.getAmount() == null)
            throw new ValidationException(ErrorCodes.VALIDATE_MISSING_AMOUNT);

        if (transaction.getAmount() == null && transaction.getTimestamp() == null)
            throw new ValidationException(ErrorCodes.VALIDATE_MISSING_AMOUNT_AND_TIMESTAMP);

        if (transaction.getTimestamp() == null || transaction.getTimestamp().toString().isEmpty())
            throw new ValidationException(ErrorCodes.VALIDATE_MISSING_TIMESTAMP);

        if (transaction.getTimestamp() < epochInMillis - ONE_MINUTE_IN_MILLI_SEC) {
            throw new InvalidTransactionException(ErrorCodes.INVALID_TRANSACTION);
        }

        transactionsList.add(transaction);

        return transaction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StatisticsData computeStatistics() {
        final StatisticsData statisticsData = new StatisticsData();

        final Double totalSum = transactionsList.stream().mapToDouble(transaction -> transaction.getAmount()).sum();
        final OptionalDouble average = transactionsList.stream().mapToDouble(transaction -> transaction.getAmount()).average();
        final OptionalDouble max = transactionsList.stream().mapToDouble(transaction -> transaction.getAmount()).max();
        final OptionalDouble min = transactionsList.stream().mapToDouble(transaction -> transaction.getAmount()).min();
        final Integer count = transactionsList.size();

        statisticsData.setSum(totalSum);
        statisticsData.setAvg(average.isPresent() ? average.getAsDouble() : 0.0);
        statisticsData.setMax(max.isPresent() ? max.getAsDouble() : 0.0);
        statisticsData.setMin(min.isPresent() ? min.getAsDouble() : 0.0);
        statisticsData.setCount(count);

        return statisticsData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOldTransactions() {
        removeMatching(transactionsList.iterator(), isOlderThanOneMinute());
    }

    // ***************************************************************************************************************
    // ****************************************** Non Public Methods *************************************************
    // ***************************************************************************************************************

    /**
     * @return predicate (boolean-valued function)
     */
    private static Predicate<Transaction> isOlderThanOneMinute() {
        final ZonedDateTime utcTimeZone = ZonedDateTime.now(ZoneOffset.UTC);
        final long epochInMilliSec = utcTimeZone.toEpochSecond() * 1000;
        return transaction -> transaction.getTimestamp() < epochInMilliSec - ONE_MINUTE_IN_MILLI_SEC;
    }

    /**
     * Iterate and remove the matching data which is collected from more than 60 seconds in the list.
     *
     * @param iterator
     * @param predicate
     */
    private static <E> void removeMatching(final Iterator<E> iterator, final Predicate<E> predicate) {
        while (iterator.hasNext()) {
            final E e = iterator.next();
            if (predicate.test(e)) {
                iterator.remove();
            }
        }
    }

    /**
     * @return transactionsList
     */
    public List<Transaction> getStatisticsForLastMinute() {
        return transactionsList;
    }
}
