package org.bongiorno.common.utils;

import org.bongiorno.common.Schedule;
import org.bongiorno.common.utils.functions.CsvToSet;
import org.bongiorno.common.utils.functions.ScheduleExpander;
import junit.framework.Assert;
import org.bongiorno.common.Schedule;
import org.bongiorno.common.utils.functions.CsvToSet;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author cbongiorno
 *         Date: 6/8/12
 *         Time: 5:46 PM
 */
public class FunctionsTest {

    @Test
    public void testCsvToSet() throws Exception {
        CsvToSet transformer = new CsvToSet();
        Set<String> results = transformer.apply("christian,peter,bongiorno");
        Set<String> expected = new HashSet<String>(Arrays.asList("christian","peter","bongiorno"));
        assertEquals(expected,results);
    }

    @Test
    public void testExpandInterval() throws Exception {


        LocalDate startDate = new LocalDate(2013,1,1);
        LocalDate endDate = new LocalDate(2013,1,3);
        LocalTime startTime = new LocalTime(12,0);
        LocalTime endTime = new LocalTime(14,0);

        Schedule schedule = new SimpleSchedule(startDate,endDate, startTime, endTime);
        Set<Interval> expected = new HashSet<>(Arrays.asList(new Interval(new DateTime(2013, 1, 2, 12, 0), new DateTime(2013, 1, 2, 14, 0))));

        ScheduleExpander expander = new ScheduleExpander(new DateTime(2013, 1, 1, 15, 0), new DateTime(2013, 1, 3, 11, 0));

        Set<Interval> results = expander.apply(schedule);

        Assert.assertEquals(expected, results);

    }

    @Test
    public void testExpandIntervalPastMidnight() throws Exception {
        LocalDate startDate = new LocalDate(2013,1,1);
        LocalDate endDate = new LocalDate(2013,1,3);
        LocalTime startTime = new LocalTime(23,0);
        LocalTime endTime = new LocalTime(1,0);

        Schedule schedule = new SimpleSchedule(startDate,endDate, startTime, endTime);

        Set<Interval> expected = new HashSet<>(Arrays.asList(
                new Interval(new DateTime(2013, 1, 1, 23, 0), new DateTime(2013, 1, 2, 1, 0)),
                new Interval( new DateTime(2013, 1, 2, 23, 0), new DateTime(2013, 1, 3, 1, 0)),
                new Interval( new DateTime(2013, 1, 3, 23, 0), new DateTime(2013, 1, 4, 1, 0)))
        );


        ScheduleExpander expander = new ScheduleExpander(new DateTime(2013, 1, 1, 15, 0), new DateTime(2013, 1, 4, 11, 0));

        Set<Interval> results = expander.apply(schedule);

        Assert.assertEquals(expected, results);
    }

    private static final class SimpleSchedule implements Schedule {

        private LocalDate startDay;

        private LocalDate endDay;

        private LocalTime startTime;

        private LocalTime endTime;

        private SimpleSchedule(LocalDate startDay, LocalDate endDay, LocalTime startTime, LocalTime endTime) {
            this.endDay = endDay;
            this.endTime = endTime;
            this.startDay = startDay;
            this.startTime = startTime;
        }

        public LocalDate getEndDay() {
            return endDay;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

        public LocalDate getStartDay() {
            return startDay;
        }

        public LocalTime getStartTime() {
            return startTime;
        }
    }
}
