package org.bongiorno.ws.core.dto;



import org.bongiorno.misc.collections.QuickCollection;

import java.util.Collection;

public class DtoQuickCollection<T> extends QuickCollection<T> implements Dto {

    public DtoQuickCollection() {
    }

    public DtoQuickCollection(Collection<T> delegate) {
        super(delegate);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
