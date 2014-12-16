package org.bongiorno.ws.core.dto;


import org.bongiorno.misc.utils.QuickMap;

import java.lang.Override;import java.lang.String;import java.util.Map;

public class DtoQuickMap<K,V> extends QuickMap<K,V> implements Dto {

    public DtoQuickMap(Map<K, V> delegate) {
        super(delegate);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
