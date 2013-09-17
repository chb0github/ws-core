package org.bongiorno.common.utils;

import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import static org.junit.Assert.assertEquals;

public class FieldOverlayerUnitTest {

    @Test
    public void testOverlay(){
        TestObject target = new TestObject("First", 1.0, 1);
        TestObject updates = new TestObject("Second", null, 2);
        TestObject expected = new TestObject("Second", 1.0, 2);

        ReflectionUtils.doWithFields(TestObject.class, new FieldOverlayer(target, updates));
        assertEquals(expected, target);
    }

    private static class TestObject{
        String first;
        Double second;
        int third;

        private TestObject(String first, Double second, int third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestObject that = (TestObject) o;

            if (third != that.third) return false;
            if (first != null ? !first.equals(that.first) : that.first != null) return false;
            if (second != null ? !second.equals(that.second) : that.second != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = first != null ? first.hashCode() : 0;
            result = 31 * result + (second != null ? second.hashCode() : 0);
            result = 31 * result + third;
            return result;
        }
    }
}
