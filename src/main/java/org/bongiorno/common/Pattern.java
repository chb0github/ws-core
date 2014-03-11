package org.bongiorno.common;

import org.bongiorno.common.validators.Validator;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public enum Pattern implements Validator<String> {

    EMAIL_REGEX(".+@.+\\..+"),
    HEX_DEC("[a-fA-F0-9]{0,}"),
    SHA_256("[a-fA-F0-9]{64}"),
    PHONE_REGEX("\\d{10}"),
    SYS_DATE_FORMAT("MM/dd/yyyy HH:mm:ss") {
        @Override
        public boolean validate(String object) {
            boolean result = false;
            DateTimeFormatter formatter = DateTimeFormat.forPattern(getPattern());
            try {
                formatter.parseDateTime(object);
                result = true;
            } catch (IllegalArgumentException e) {
                // ignore
            }
            return result;
        }
    };


    private String pattern;


    private Pattern(java.lang.String pattern) {
        this.pattern = pattern;
    }


    @Override
    public boolean validate(String object) {
        return object.matches(pattern);
    }

    public String getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return pattern;
    }
}
