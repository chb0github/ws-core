package org.bongiorno.common.utils.functions;

import org.bongiorno.common.Identifiable;
import org.bongiorno.common.utils.Function;

import javax.annotation.Nullable;

public class IdentifiableToId implements Function<Identifiable, Long> {

    private static IdentifiableToId INSTANCE = new IdentifiableToId();

    private IdentifiableToId() {
    }

    public static IdentifiableToId getInstance() {
        return INSTANCE;
    }

    @Override
    public Long apply(@Nullable Identifiable input) {
        return input == null ? null : input.getId();
    }

}
