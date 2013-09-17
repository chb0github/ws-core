package org.bongiorno.common.utils.functions.predicates;

import org.bongiorno.common.utils.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EqualsPredicate<T> implements Function<T, Boolean> {

    private T target;

    public EqualsPredicate(@Nonnull T target) {
        this.target = target;
    }

    @Override
    public Boolean apply(@Nullable T input) {
        return target.equals(input);
    }
}
