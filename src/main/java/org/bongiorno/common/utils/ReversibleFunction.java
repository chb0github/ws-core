package org.bongiorno.common.utils;

import javax.annotation.Nullable;

public interface ReversibleFunction<F, T> extends Function<F, T> {
    public F reverse(@Nullable T input);
}
