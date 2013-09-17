package org.bongiorno.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * @author cbongiorno
 *         Date: 4/22/12
 *         Time: 2:19 PM
 */
public class StringUtils {

    private static Logger log = LoggerFactory.getLogger(StringUtils.class);

    private StringUtils() {
    }

    public static String toAlphaNumeric(String val) {
        return val.replaceAll("[^a-zA-Z0-9]", "");
    }

    public static String urlEncode(String url) {
        String retVal;
        try {
            retVal = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.info("UTF-8 not supported"); //Not warn-worthy, but it's weird and should never happen.
            //noinspection deprecation
            retVal = URLEncoder.encode(url);
        }
        return retVal;
    }

    public static String urlDecode(String url) {
        try {
            url = URLDecoder.decode(url, Charset.defaultCharset().name());
        } catch (UnsupportedEncodingException e) {
            // ignore the error. There is nothing we can do about this
        }
        return url;
    }

    /*
    * To convert the InputStream to String we use the
    * Reader.read(char[] buffer) method. We iterate until the
    * Reader return -1 which means there's no more data to
    * read. We use the StringWriter class to produce the string.
    */
    public static String convertStreamToString(InputStream is) throws IOException {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        int n;
        while ((n = reader.read(buffer)) != -1) {
            writer.write(buffer, 0, n);
        }
        return writer.toString();
    }

}
