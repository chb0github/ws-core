package org.bongiorno.ws.core.dto;


import org.bongiorno.ws.core.QuickMap;

import java.util.Map;

public class DtoQuickMap<K,V> extends QuickMap<K,V> implements Dto {

    public DtoQuickMap(Map<K, V> delegate) {
        super(delegate);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
