package org.bongiorno.ws.core.dto.support;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DefaultLocalTimeAdapter extends XmlAdapter<String, LocalTime> {

    public static final String DEFAULT_FORMAT = "HH:mm:ss";

    private DateTimeFormatter formatter;

    public DefaultLocalTimeAdapter(){
        this(DEFAULT_FORMAT);
    }

    public DefaultLocalTimeAdapter(String format) {
        formatter = DateTimeFormat.forPattern(format);
    }

    @Override
    public LocalTime unmarshal(String v) throws Exception {
        try {
            return LocalTime.parse(v, formatter);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + "; valid format example: " + formatter.print(new LocalTime()), e);
        }
    }

    @Override
    public String marshal(LocalTime v) throws Exception {
        return formatter.print(v);
    }
}
