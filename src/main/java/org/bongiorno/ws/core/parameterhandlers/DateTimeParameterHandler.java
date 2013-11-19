package org.bongiorno.ws.core.parameterhandlers;

import org.bongiorno.common.Pattern;
import org.bongiorno.ws.core.exceptions.BadRequestException;
import org.apache.cxf.jaxrs.ext.ParameterHandler;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.ws.rs.ext.Provider;

@Provider
public class DateTimeParameterHandler implements ParameterHandler<DateTime> {

    private DateTimeFormatter formatter = DateTimeFormat.forPattern(Pattern.SYS_DATE_FORMAT.getPattern());

    @Override
    public DateTime fromString(String s) {
        DateTime results = null;
        try {
            results = formatter.parseDateTime(s);
        }
        catch (IllegalArgumentException e) {
            String msg = e.getMessage() + " -- Valid format: '" + Pattern.SYS_DATE_FORMAT + "' example:  '"+ formatter.print(new DateTime()) + "'";
            throw new BadRequestException(msg);
        }
        return results;
    }
}
