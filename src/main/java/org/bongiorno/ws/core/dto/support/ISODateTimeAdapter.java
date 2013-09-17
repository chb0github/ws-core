package org.bongiorno.ws.core.dto.support;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ISODateTimeAdapter extends XmlAdapter<String, DateTime> {

    @Override
    public DateTime unmarshal(String input) throws Exception {
        return ISODateTimeFormat.dateTime().parseDateTime(input);
    }

    @Override
    public String marshal(DateTime input) throws Exception {
        return ISODateTimeFormat.dateTime().print(input);
    }
}
