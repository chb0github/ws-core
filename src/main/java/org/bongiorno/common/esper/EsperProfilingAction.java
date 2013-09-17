package org.bongiorno.common.esper;

import com.espertech.esper.client.*;
import org.bongiorno.common.aspects.profiling.actions.ProfilingAction;
import org.bongiorno.common.esper.events.MethodCalledEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class EsperProfilingAction implements ProfilingAction {

    @Autowired
    private EPRuntime esperRuntime;

    public EsperProfilingAction(EPAdministrator esperAdmin, Long window, Collection<UpdateListener> listeners){
        EPPreparedStatement template = esperAdmin.prepareEPL(
                "select targetClass, methodName, avg(duration), count(*), ? as window" +
                "  from MethodCalledEvent.win:time_batch(? msec)" +
                "  group by streamDiscriminator, targetClass, methodName having streamDiscriminator = ?");
        template.setObject(1, window);
        template.setObject(2, window);
        template.setObject(3, this);
        EPStatement statement = esperAdmin.create(template);
        for (UpdateListener listener : listeners) {
            statement.addListener(listener);
        }
    }

    @Override
    public void perform(Class type, String methodName, Object[] args, long duration, Throwable error) {
        esperRuntime.sendEvent(new MethodCalledEvent(this, type, methodName, args, duration, error));
    }

    //Every bean of this class within an ApplicationContext will have the same state.  I'm paranoid that
    //somehow someone will write some code that decides that makes them equal.
    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
}
