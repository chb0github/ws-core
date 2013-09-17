package org.bongiorno.ws.core.dto.support;

import org.bongiorno.common.Constants;
import org.bongiorno.common.utils.Function;
import org.bongiorno.common.Constants;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DefaultDateTimeAdapter extends XmlAdapter<String, DateTime> implements Function<DateTime,String> {

    private DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.AZ_DATE_FORMAT);

    private DateTimeZone timeZone;

    public DefaultDateTimeAdapter(){
    }

    public DefaultDateTimeAdapter(String format, String timeZone) {
        formatter = DateTimeFormat.forPattern(format);
        this.timeZone = DateTimeZone.forID(timeZone);
    }

    @Override
    public DateTime unmarshal(String v) {
        try {
            return DateTime.parse(v,formatter);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + "; valid format example: " + formatter.print(DateTime.now()), e);
        }
    }

    @Override
    public String marshal(DateTime v) {
        return formatter.print(v.toDateTime(timeZone));
    }

    @Override
    public String apply(DateTime source) {
        return marshal(source);
    }

    public DateTime normalize(DateTime in){
        return unmarshal(marshal(in));
    }


    public static DateTime normalizeDate(DateTime in) {
        return new DefaultDateTimeAdapter().normalize(in);
    }
}
