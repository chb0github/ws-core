package org.bongiorno.common.utils.functions;

import org.bongiorno.common.utils.ReversibleFunction;

import javax.annotation.Nullable;

public class BooleanToString implements ReversibleFunction<Boolean, String>{

    @Override
    public Boolean reverse(@Nullable String input) {
        return input == null ? null : Boolean.parseBoolean(input);
    }

    @Override
    public String apply(@Nullable Boolean input) {
        return input == null ? null : input.toString();
    }
}
