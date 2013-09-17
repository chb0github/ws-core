package org.bongiorno.common.utils.functions;

import org.bongiorno.common.Transformable;
import org.bongiorno.common.utils.Function;

import javax.annotation.Nullable;

public class TransformFunction<T> implements Function<Transformable<T>, T>{

    private static final TransformFunction instance = new TransformFunction();

    private TransformFunction(){

    }

    @SuppressWarnings("unchecked")
    public static <X> TransformFunction<X> getInstance(){
        return instance;
    }

    @Override
    public T apply(@Nullable Transformable<T> input) {
        return input == null ? null : input.transform();
    }
}
