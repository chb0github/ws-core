package org.bongiorno.common.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author chribong
 */
public class RandomTest {

    @Test
    public void testSelectAFew() throws Exception {

        // test as List<>
        List<String> things = Arrays.asList("A", "B", "AA", "BB", "AAA", "BBB");
        List<String> results = WSRandom.selectAFew(3, things);
        assertEquals(3, results.size());
        assertTrue(things.containsAll(results));

        // test as []
        List<String> strings = WSRandom.selectAFew(3, "A", "B", "AA", "BB", "AAA", "BBB");
        assertEquals(3, strings.size());
        assertTrue(things.containsAll(strings));

        // test as iterable

        try {
            WSRandom.selectAFew(3, new HashSet<>(things));
        }
        catch (IllegalArgumentException e) {
            // this method can only execute on a list or an array.
            // it is not possible for a iterable to produce a consistent result
            // and the JVM will interpret the above line as T ... = size() 1
            // name 1 element of type iterable.
        }

    }

}
