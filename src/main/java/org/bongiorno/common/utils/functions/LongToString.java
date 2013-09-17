package org.bongiorno.common.utils.functions;

import org.bongiorno.common.utils.ReversibleFunction;

import javax.annotation.Nullable;

public class LongToString implements ReversibleFunction<Long, String> {
    @Override
    public Long reverse(@Nullable String input) {
        return input == null ? null : Long.parseLong(input);
    }

    @Override
    public String apply(@Nullable Long input) {
        return input == null ? null : input.toString();
    }
}
