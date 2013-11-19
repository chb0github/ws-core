package org.bongiorno.common.utils;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class StringsUtilsTest {

    @Test
    public void testToAlphaNumeric() {
        assertEquals("abc", StringUtils.toAlphaNumeric("a-b c"));
    }

    @Test
    public void testUrlEncode() throws UnsupportedEncodingException {
        assertNotNull(StringUtils.urlEncode("http://my.com/test?bac=dddde"));
    }

}
