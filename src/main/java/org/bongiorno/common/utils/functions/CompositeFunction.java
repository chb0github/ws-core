package org.bongiorno.common.utils.functions;

import org.bongiorno.common.utils.Function;

import javax.annotation.Nullable;

public class CompositeFunction<F, T> implements Function<F, T> {

    protected final Function first;
    protected final Function second;

    public <X> CompositeFunction(Function<F, ? extends X> first, Function<? super X, T> second){
        this.first = first;
        this.second = second;
    }

    @Override
    public T apply(@Nullable F input) {
        return (T) second.apply(first.apply(input));
    }
}
