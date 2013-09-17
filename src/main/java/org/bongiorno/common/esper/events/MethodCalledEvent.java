package org.bongiorno.common.esper.events;

import java.util.Arrays;

public class MethodCalledEvent {

    private Object streamDiscriminator;

    private Class targetClass;

    private String methodName;

    private Object[] args;

    private long duration;

    private Throwable exception;

    public MethodCalledEvent(Object streamDiscriminator, Class targetClass, String methodName, Object[] args, long duration, Throwable exception) {
        this.streamDiscriminator = streamDiscriminator;
        this.targetClass = targetClass;
        this.methodName = methodName;
        this.args = args;
        this.duration = duration;
        this.exception = exception;
    }

    public Object getStreamDiscriminator() {
        return streamDiscriminator;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public long getDuration() {
        return duration;
    }

    public Throwable getException() {
        return exception;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodCalledEvent event = (MethodCalledEvent) o;

        if (duration != event.duration) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(args, event.args)) return false;
        if (exception != null ? !exception.equals(event.exception) : event.exception != null) return false;
        if (methodName != null ? !methodName.equals(event.methodName) : event.methodName != null) return false;
        if (streamDiscriminator != null ? !streamDiscriminator.equals(event.streamDiscriminator) : event.streamDiscriminator != null)
            return false;
        if (targetClass != null ? !targetClass.equals(event.targetClass) : event.targetClass != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = streamDiscriminator != null ? streamDiscriminator.hashCode() : 0;
        result = 31 * result + (targetClass != null ? targetClass.hashCode() : 0);
        result = 31 * result + (methodName != null ? methodName.hashCode() : 0);
        result = 31 * result + (args != null ? Arrays.hashCode(args) : 0);
        result = 31 * result + (int) (duration ^ (duration >>> 32));
        result = 31 * result + (exception != null ? exception.hashCode() : 0);
        return result;
    }
}
