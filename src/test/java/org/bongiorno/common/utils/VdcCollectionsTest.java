package org.bongiorno.common.utils;

import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author cbongiorno
 *         Date: 7/1/12
 *         Time: 2:31 PM
 */
public class VdcCollectionsTest {

    @Test
    public void testDelimiter() {
        Collection c = delimitedCollection(Arrays.asList(1, 2, 3, 4, 5), "#:");
        assertEquals(c.toString(), "1#:2#:3#:4#:5");
    }

    @Test
    public void testDelimitedCollectionEmptyCollection() throws Exception {
        Collection c = delimitedCollection(new LinkedList<String>(), "#:");
        assertEquals(c.toString(), "");
    }

    @Test
    public void testDelimitedMap() throws Exception {
        Map<Object,Object> m = delimitedMap(new LinkedHashMap<Object, Object>(), "#:", "$$");
        m.put("Christian", "Bongiorno");
        m.put("Viola", "Bongiorno");
        m.put("Adrian", "Bongiorno");
        m.put("Maya", "Bongiorno");
        assertEquals(m.toString(), "Christian#:Bongiorno$$Viola#:Bongiorno$$Adrian#:Bongiorno$$Maya#:Bongiorno$$");

        m = delimitedMap(new LinkedHashMap<Object, Object>(), "!!", "#:", "$$");
        m.put("Christian", "Bongiorno");
        m.put("Viola", "Bongiorno");
        m.put("Adrian", "Bongiorno");
        m.put("Maya", "Bongiorno");
        assertEquals(m.toString(), "!!Christian#:Bongiorno$$!!Viola#:Bongiorno$$!!Adrian#:Bongiorno$$!!Maya#:Bongiorno$$");
    }

    @Test
    public void testOnDupMap() {
        Map<String, Void> test = exceptionOnDuplicateMap(new HashMap<String, Void>());
        test.put("Christian", null);
        test.put("George", null);
        try {
            test.put("Christian", null);
            fail();
        } catch (IllegalArgumentException e) {
            // better throw an exception
        }

    }

    @Test
    public void testOnDubSortedMap() {
        Map<String, Void> test = exceptionOnDuplicateMap(new TreeMap<String, Void>());
        test.put("Christian", null);
        test.put("George", null);
        try {
            test.put("Christian", null);
            fail();
        } catch (IllegalArgumentException e) {
            // better throw an exception
        }
    }

    @Test
    public void testOnDupSet() {
        Set<String> test = exceptionOnDuplicateSet(new HashSet<String>());
        test.add("Christian");
        test.add("George");
        try {
            test.add("Christian");
            fail();
        } catch (IllegalArgumentException e) {
            // better throw an exception
        }
        try {
            test.addAll(Arrays.asList("George","Christian"));
            fail();
        }
        catch (IllegalArgumentException e) {
            // expected
        }
    }
}
