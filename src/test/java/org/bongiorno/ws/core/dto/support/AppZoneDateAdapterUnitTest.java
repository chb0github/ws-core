package org.bongiorno.ws.core.dto.support;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author cbongiorno
 *         Date: 5/17/12
 *         Time: 2:49 PM
 */
public class AppZoneDateAdapterUnitTest {

    @Test
    public void testDateAdapter() throws Exception {
        DefaultDateTimeAdapter da = new DefaultDateTimeAdapter("MM/dd/yyyy HH:mm", null);
        DateTime first = da.unmarshal(da.marshal(DateTime.now()));
        DateTime second = da.unmarshal(da.marshal(first));
        assertEquals(first,second);

    }

    @Test
    public void testLocalDateAdapter() throws Exception{
        DefaultLocalDateAdapter adapter = new DefaultLocalDateAdapter("MM/dd/yyyy");

        String string = "09/20/1984";
        LocalDate localDate = new LocalDate(1984, 9, 20);

        assertEquals(localDate, adapter.unmarshal(string));
        assertEquals(string, adapter.marshal(localDate));
    }

    @Test
    public void testLocalTimeAdapter() throws Exception{
        DefaultLocalTimeAdapter adapter = new DefaultLocalTimeAdapter("HH:mm");

        String string = "16:20";
        LocalTime localTime = new LocalTime(16, 20);

        assertEquals(localTime, adapter.unmarshal(string));
        assertEquals(string, adapter.marshal(localTime));
    }
}
