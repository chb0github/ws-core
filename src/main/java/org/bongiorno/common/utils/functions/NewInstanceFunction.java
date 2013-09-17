package org.bongiorno.common.utils.functions;

import org.bongiorno.common.utils.Function;

import javax.annotation.Nullable;

public class NewInstanceFunction<T> implements Function<Object, T> {

    private Class<T> clazz;

    private NewInstanceFunction(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static <X> NewInstanceFunction<X> forClass(Class<X> clazz) {
        return new NewInstanceFunction<X>(clazz);
    }

    @Override
    public T apply(@Nullable Object input) {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
