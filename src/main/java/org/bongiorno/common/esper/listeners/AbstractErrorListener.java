package org.bongiorno.common.esper.listeners;

import com.espertech.esper.client.*;
import org.bongiorno.common.esper.events.MethodCalledEvent;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.bongiorno.common.utils.OtherUtils.getMyHostname;

public abstract class AbstractErrorListener implements UpdateListener{


    public AbstractErrorListener(EPAdministrator epAdmin, Class<? extends Throwable> errorType){
        EPPreparedStatement preparedStatement = epAdmin.prepareEPL("select * from MethodCalledEvent where exception is not null and (?).isAssignableFrom(exception.getClass())");
        preparedStatement.setObject(1, errorType);
        EPStatement epStatement = epAdmin.create(preparedStatement);
        epStatement.addListener(this);
    }

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        if(newEvents != null){
            for (EventBean newEvent : newEvents) {
                MethodCalledEvent event = (MethodCalledEvent) newEvent.getUnderlying();
                Throwable exception = event.getException();
                String subject = String.format("%s in %s.%s on %s", exception.getClass().getSimpleName(), event.getTargetClass().getSimpleName(), event.getMethodName(), getMyHostname());

                StringWriter bodyStr = new StringWriter();
                PrintWriter bodyWriter = new PrintWriter(bodyStr);
                bodyWriter.println("Stack trace:");
                exception.printStackTrace(bodyWriter);

                notify(subject, bodyStr.toString());
            }

        }

    }
    protected abstract void notify(String subject, String details);
}
