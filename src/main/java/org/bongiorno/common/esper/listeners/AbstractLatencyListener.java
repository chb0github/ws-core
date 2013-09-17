package org.bongiorno.common.esper.listeners;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public abstract class AbstractLatencyListener implements UpdateListener{

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        if(newEvents != null){
            for (EventBean newEvent : newEvents) {
                Class targetClass = (Class) newEvent.get("targetClass");
                String methodName = (String) newEvent.get("methodName");
                Number latency = (Number) newEvent.get("avg(duration)");
                Number window = (Number) newEvent.get("window");
                Number callCount = (Number) newEvent.get("count(*)");

                notify(targetClass, methodName, latency, callCount.intValue(), window.intValue());
            }
        }
    }
    protected abstract void notify(Class clazz, String methodName, Number latency, int callCount, int windowSize);
}
