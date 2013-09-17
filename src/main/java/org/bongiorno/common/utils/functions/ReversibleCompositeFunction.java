package org.bongiorno.common.utils.functions;

import org.bongiorno.common.utils.Function;
import org.bongiorno.common.utils.ReversibleFunction;

import javax.annotation.Nullable;

public class ReversibleCompositeFunction<F, T> extends CompositeFunction<F, T> implements ReversibleFunction<F, T>{

    public <X> ReversibleCompositeFunction(Function<F, ? extends X> first, Function<? super X, T> second) {
        super(first, second);
    }

    @Override
    public F reverse(@Nullable T input) {
        ReversibleFunction rFirst = (ReversibleFunction) first;
        ReversibleFunction rSecond = (ReversibleFunction) second;
        return (F) rFirst.reverse(rSecond.reverse(input));
    }
}
