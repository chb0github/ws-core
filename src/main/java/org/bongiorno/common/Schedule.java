package org.bongiorno.common;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * @author cbongiorno
 */
public interface Schedule {

    public LocalTime getEndTime();

    public LocalDate getStartDay();

    public LocalDate getEndDay();

    public LocalTime getStartTime();
}
