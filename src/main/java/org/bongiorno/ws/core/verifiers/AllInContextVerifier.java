package org.bongiorno.ws.core.verifiers;

import org.bongiorno.ws.core.controllers.SystemVerifier;
import org.bongiorno.ws.core.controllers.SystemVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AllInContextVerifier extends AbstractSystemVerifier {

    @Autowired
    private ApplicationContext context;

    private Collection<SystemVerifier> delegates;

    @Override
    public Map<String, Object> execute(MultivaluedMap<String, String> params) {
        ensureDelegates();
        Map<String, Object> retVal = new HashMap<>();
        for (SystemVerifier verifier : delegates) {
            retVal.put(verifier.getName(), verifier.execute(params));
        }
        return retVal;
    }

    private void ensureDelegates() {
        if(delegates == null){
            delegates = context.getBeansOfType(SystemVerifier.class, false, true).values();
            delegates.remove(this);
        }
    }
}
