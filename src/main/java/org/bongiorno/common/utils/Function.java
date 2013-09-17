package org.bongiorno.common.utils;

import javax.annotation.Nullable;

public interface Function<F, T> {

    /**
     * Applies 'this' to F and returns T
     */
    T apply(@Nullable F input);
}