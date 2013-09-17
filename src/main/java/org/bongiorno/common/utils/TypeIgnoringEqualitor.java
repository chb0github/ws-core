package org.bongiorno.common.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * For the purposes of this class, an "Equalitor" is like a Comparator in that it determines whether objects are equal,
 * but doesn't say anything about their order.
 */
public class TypeIgnoringEqualitor<T>{

    private Class<T> type;
    private Set<Class> ignoredTypes;

    public TypeIgnoringEqualitor(Class<T> type, Class... ignoredTypes) {
        this.type = type;
        this.ignoredTypes = new HashSet<Class>(Arrays.asList(ignoredTypes));
    }

    public TypeIgnoringEqualitor(Class<T> type, Set<Class> ignoredTypes) {
        this.type = type;
        this.ignoredTypes = ignoredTypes;
    }

    public boolean equal(T o1, Object o2) {
        if(o1 == o2)
            return true;
        if(o1 == null || o2 == null || o1.getClass() != o2.getClass())
            return false;

        FieldMatcher matcher = new FieldMatcher(o1, o2);
        ReflectionUtils.doWithFields(type, matcher);

        return matcher.paramsEqual();
    }

    public Set<Class> getIgnoredTypes() {
        return ignoredTypes;
    }

    private class FieldMatcher implements ReflectionUtils.FieldCallback {

        private Object first;
        private Object second;

        Set<Field> mismatches = new HashSet<Field>();

        private FieldMatcher(Object first, Object second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
            if (!ignoredTypes.contains(field.getType())) {
                field.setAccessible(true);
                Object firstVal = field.get(first);
                Object secondVal = field.get(second);
                if (firstVal != secondVal && (firstVal == null || !firstVal.equals(secondVal))) {
                    mismatches.add(field);
                }
            }
        }

        public boolean paramsEqual() {
            return mismatches.isEmpty();
        }
    }
}
