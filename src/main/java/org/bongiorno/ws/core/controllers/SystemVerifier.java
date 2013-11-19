package org.bongiorno.ws.core.controllers;

import javax.ws.rs.core.MultivaluedMap;

public interface SystemVerifier {

    public Object execute(MultivaluedMap<String, String> params);

    String getName();
}
