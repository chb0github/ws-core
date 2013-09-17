package org.bongiorno.ws.core.dto.support;

import org.joda.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ISOLocalDateAdapter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String in) throws Exception {
        return LocalDate.parse(in);
    }

    @Override
    public String marshal(LocalDate in) throws Exception {
        return in.toString();
    }
}
