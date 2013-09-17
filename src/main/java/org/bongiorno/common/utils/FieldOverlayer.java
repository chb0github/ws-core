package org.bongiorno.common.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Assigns non-null values of fields in the "source" object to the corresponding fields in the "target" object.
 * Applying this to all fields in the target class has the effect of updating the target object with the values
 * specified by the source object, but allowing target to keep its original values when source does not specify one.
 */
public class FieldOverlayer implements ReflectionUtils.FieldCallback {

    private Object target;
    private Object source;

    public FieldOverlayer(Object target, Object source) {
        this.target = target;
        this.source = source;
    }

    @Override
    public void doWith(Field field) throws IllegalAccessException {
        if (!Modifier.isStatic(field.getModifiers())) {
            field.setAccessible(true);
            Object sourceValue = field.get(source);
            if (sourceValue != null) {
                field.set(target, sourceValue);
            }
        }
    }
}