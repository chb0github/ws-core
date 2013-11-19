package org.bongiorno.ws.core.verifiers;

import org.bongiorno.ws.core.controllers.SystemVerifier;


public abstract class AbstractSystemVerifier implements SystemVerifier {

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
