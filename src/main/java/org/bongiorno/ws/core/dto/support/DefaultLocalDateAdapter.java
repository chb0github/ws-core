package org.bongiorno.ws.core.dto.support;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DefaultLocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private static final Logger log = LoggerFactory.getLogger(DefaultLocalDateAdapter.class);

    public static final String DEFAULT_FORMAT = "MM/dd/yyyy";

    private DateTimeFormatter formatter;

    public DefaultLocalDateAdapter(){
        this(DEFAULT_FORMAT);
    }

    public DefaultLocalDateAdapter(String format) {
        formatter = DateTimeFormat.forPattern(format);
    }

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        try {
            return LocalDate.parse(v, formatter);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + "; valid format example: " + formatter.print(new LocalDate()), e);
        }
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return formatter.print(v);
    }
}
