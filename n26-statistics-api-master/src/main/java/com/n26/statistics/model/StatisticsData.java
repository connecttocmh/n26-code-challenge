/**
 * Copyright 2018 N26 Bank coding challenge. All rights reserved.
 */

package com.n26.statistics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Chetankumar Hiremath
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsData {
    // ***************************************************************************************************************
    // ******************************************** Public Fields ****************************************************
    // ***************************************************************************************************************

    // ***************************************************************************************************************
    // ****************************************** Non Public Fields **************************************************
    // ***************************************************************************************************************

    private double sum = 0.0;

    private double avg;

    private double max = Double.MIN_VALUE;

    private double min = Double.MAX_VALUE;

    private long count = 0l;

    // ***************************************************************************************************************
    // *********************************************** Constructors **************************************************
    // ***************************************************************************************************************

    // ***************************************************************************************************************
    // ******************************************** Public Methods ***************************************************
    // ***************************************************************************************************************

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;

        final StatisticsData statisticsData = (StatisticsData) object;

        if (Double.compare(statisticsData.sum, sum) != 0)
            return false;
        if (count != statisticsData.count)
            return false;
        if (Double.compare(statisticsData.max, max) != 0)
            return false;
        if (Double.compare(statisticsData.min, min) != 0)
            return false;
        return Double.compare(statisticsData.avg, avg) == 0;

    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(sum);
        int result = (int) (temp ^ temp >>> 32);
        result = 31 * result + (int) (count ^ count >>> 32);

        temp = Double.doubleToLongBits(max);
        result = 31 * result + (int) (temp ^ temp >>> 32);

        temp = Double.doubleToLongBits(min);
        result = 31 * result + (int) (temp ^ temp >>> 32);

        temp = Double.doubleToLongBits(avg);
        result = 31 * result + (int) (temp ^ temp >>> 32);

        return result;
    }

    // ***************************************************************************************************************
    // ****************************************** Non Public Methods *************************************************
    // ***************************************************************************************************************
}
