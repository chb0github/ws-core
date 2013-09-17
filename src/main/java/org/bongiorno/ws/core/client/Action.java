package org.bongiorno.ws.core.client;


/**
 * @author cbongiorno
 *         Date: 7/2/12
 *         Time: 2:42 PM
 */
public interface Action<RSP, PYLD> {

    public RSP submit(PYLD tx);
}
