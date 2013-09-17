package org.bongiorno.common.utils.functions;

import org.bongiorno.common.Schedule;
import org.bongiorno.common.utils.Function;
import org.bongiorno.common.Schedule;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author cbongiorno
 */
public class ScheduleExpander implements Function<Schedule, Set<Interval>> {

    private DateTime from;
    private DateTime to;

    public ScheduleExpander(DateTime from, DateTime to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Set<Interval> apply(@NotNull Schedule input) {
        if (input == null)
            throw new IllegalArgumentException("Can't have a null schedule");

        Set<Interval> result = new HashSet<>();
        // if no time of day is defined then it's for the entire day
        LocalDate startDay = input.getStartDay();
        LocalDate endDay = input.getEndDay();

        if (input.getStartTime() == null || input.getEndTime() == null) {
            result = Collections.singleton(new Interval(startDay.toDateTimeAtStartOfDay(), endDay.plusDays(1).toDateTimeAtStartOfDay()));
        } else {
            // max(from, startDay) does the start day come BEFORE the 'from' date? If so, use 'from'
            LocalDate rangeStart = startDay.compareTo(from.toLocalDate()) < 0 ? from.toLocalDate() : startDay;
            // min(endDay, to) does the end day come BEFORE the 'to' date? If so, use 'to'
            LocalDate rangeEnd = endDay.compareTo(to.toLocalDate()) < 0 ? endDay : to.toLocalDate();

            // iterate 1 day at a time
            for (LocalDate day = rangeStart; day.compareTo(rangeEnd) <= 0; day = day.plusDays(1)) {
                DateTime start = day.toDateTime(input.getStartTime());
                DateTime end = day.toDateTime(input.getEndTime());
                // Checking for crossing a midnight boundary
                if (input.getEndTime().compareTo(input.getStartTime()) < 0) {
                    end = end.plusDays(1);
                }
                // basically we need to have an overlapping interval to include it in the results
                if (from.compareTo(end) <= 0 && to.compareTo(start) >= 0)
                    result.add(new Interval(start, end));
            }
        }
        return result;
    }
}
