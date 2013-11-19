package org.bongiorno.common.utils;

import org.bongiorno.common.utils.functions.predicates.IsNullPredicate;
import org.bongiorno.common.utils.functions.predicates.NotPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class OtherUtils {

    private static final Logger log = LoggerFactory.getLogger(OtherUtils.class);

    private static final byte[] PNG_SIGNATURE = new byte[]{-119, 80, 78, 71, 13, 10, 26, 10};

    /**
     * This utility method will compare equality on all private non-static fields.
     * Note: If the security manager is active then this method will throw exceptions.
     * Sadly the only TRUE way to make sure this runs is to C&P it into your class. Best used on
     * classes with large fields that you don't want to have an ugly generated method for
     * <p/>
     * caveat emptor
     *
     * @param a 'this' usually
     * @param b the other object to test equality on
     * @return true if all members as described above are equal
     * @throws IllegalAccessException if the caller of this function is unable to access the members of 'a' or 'b'
     */
    public static boolean fieldEquals(Object a, Object b) throws IllegalAccessException {
        boolean eq = true;
        Field[] fields = a.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length && eq; i++) {
            Field field = fields[i];
            int mods = field.getModifiers();
            if (Modifier.isPrivate(mods) && !Modifier.isStatic(mods)) {
                field.setAccessible(true);
                Object me = field.get(a);
                Object he = field.get(b);
                field.setAccessible(false);
                // both same instance or null or both not null and equal
                eq = me == he || (me != null && he != null && me.equals(he));

            }
        }
        return eq;
    }

    public static int fieldHashCode(Object a) throws IllegalAccessException {
        int hash = 1;
        Field[] fields = a.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mods = field.getModifiers();
            if (Modifier.isPrivate(mods) && !Modifier.isStatic(mods)) {
                field.setAccessible(true);
                Object me = field.get(a);
                field.setAccessible(false);
                if (me != null)
                    hash *= 31 * me.hashCode() >> 7;
            }
        }
        return hash;
    }

    public static int hashCodeSeries(Object... cherrios) {
        int hash = 1;
        for (Object field : cherrios) {
            hash *= 31 * field.hashCode() >> 7;
        }
        return hash;
    }

    /**
     * A very simple implementation for reading bytes from a file. Size limitations apply and no
     * guarantee of success is made. If you want it more robust, upgrade it yourself
     *
     * @param f the file to read
     * @return a byte[] representing all the data in the file
     * @throws java.io.IOException for all sorts of reasons
     */
    public static byte[] getDataFromFile(File f) throws IOException {
        // this implementation COULD use getDataFromStream but would be really in-efficient
        InputStream is = new FileInputStream(f);

        byte[] data = new byte[(int) f.length()];

        int read = is.read(data);
        int available = is.available();
        // this loop should never actually execute.
        while (read < available) {
            read += is.read(data, read, available);
            available = is.available();
        }

        is.close();
        return data;
    }

    /**
     * Continues to read 1024 byte chunks and throws them into a BAOS until no more data is available on the
     * stream. If you know you're working with a File it would be more efficient to use that as this implementation
     * uses a generalized approach to the problem
     *
     * @param is the stream to read from
     * @return the data in the file.
     * @throws java.io.IOException
     */
    public static byte[] getDataFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int read = is.read(data);
        int total = read;
        baos.write(data);

        for (int available = is.available(); available > 0; available = is.available()) {
            int willRead = Math.min(data.length, available);
            // len > b.length - off
            read = is.read(data, 0, willRead);
            total += read;
            baos.write(data, 0, read);
        }

        is.close();
        byte[] results = new byte[total];
        System.arraycopy(baos.toByteArray(), 0, results, 0, results.length);
        return results;
    }

    /**
     * Just like it sounds. No validation is done. Just kinda assumes that what's in the file is what you want
     *
     * @param input the stream to read from
     * @return your string as represented in the file
     * @throws java.io.IOException
     */
    public static String streamToString(InputStream input) throws IOException {
        byte[] data = getDataFromStream(input);
        return new String(data);
    }

    /**
     * Get a set of all the fields in an object that have not been initialized.  Useful when testing transformers, to
     * make sure you're not missing anything (and let you know what you are missing).
     *
     * @param o The object to check for null fields
     * @return All the fields that are null
     * @throws IllegalAccessException If the security manager doesn't like you looking at private fields.
     */
    public static Collection<Field> nullFields(Object o) throws IllegalAccessException {
        //Primary use is to show me everything I forgot in a transformer test, so I want this to be easy to read.
        Collection<Field> result = WSCollections.delimitedCollection(new LinkedList<Field>(), "\n");
        for (Field field : o.getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                if (field.get(o) == null) {
                    result.add(field);
                }
            }
        }
        return result;
    }

    public static boolean zeroOrAllNull(Object... things) {
        return zeroOrAllNull(Arrays.asList(things));
    }

    public static boolean zeroOrAllNull(Collection<?> things) {
        return things == null
                || !things.contains(null)
                || WSCollections.findOne(things, not(IsNullPredicate.getInstance())) == null;
    }

    public static <T> Function<T, Boolean> not(Function<T, Boolean> positive) {
        return new NotPredicate<>(positive);
    }

    public static String getMyHostname() {
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error("Unknown *local* host?  Really?", e);
        }
        return hostName;
    }

    public static boolean isImgPng(byte[] imgData) {
        boolean match = imgData.length >= PNG_SIGNATURE.length;
        for (int i = 0; match && i < PNG_SIGNATURE.length; ++i) {
            match = imgData[i] == PNG_SIGNATURE[i];
        }

        return match;
    }
}
