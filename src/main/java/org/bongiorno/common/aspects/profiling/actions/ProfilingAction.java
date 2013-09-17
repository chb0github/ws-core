package org.bongiorno.common.aspects.profiling.actions;

/**
 * @author cbongiorno
 */
public interface ProfilingAction {

    public void perform(Class type, String methodName, Object[] args, long duration, Throwable error);

}
