package org.bongiorno.ws.core.parameterhandlers;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class TypeHandlerTest {

    @InjectMocks
    private DateTimeParameterHandler dph = new DateTimeParameterHandler();

    @Test
    public void testDateTypeHandler() throws Exception {
        DateTime d = dph.fromString("5/14/2012 04:29:00");
        assertEquals(4,d.getHourOfDay());
        assertEquals(29,d.getMinuteOfHour());
        assertEquals(14,d.getDayOfMonth());
        assertEquals(5,d.getMonthOfYear());
        assertEquals(2012,d.getYear());

    }
}
