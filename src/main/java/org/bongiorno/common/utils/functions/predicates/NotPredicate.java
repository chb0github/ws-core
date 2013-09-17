package org.bongiorno.common.utils.functions.predicates;

import org.bongiorno.common.utils.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NotPredicate<T> implements Function<T, Boolean> {

    private Function<? super T, Boolean> positive;

    public NotPredicate(@Nonnull Function<? super T, Boolean> positive) {
        this.positive = positive;
    }

    @Override
    public Boolean apply(@Nullable T input) {
        return ! positive.apply(input);
    }
}
