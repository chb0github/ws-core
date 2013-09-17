package org.bongiorno.common.aspects.profiling.actions;

public class ProfileSysOutAction implements ProfilingAction {


    @Override
    public void perform(Class type, String methodName, Object[] args, long duration, Throwable error) {
        System.out.format("%s.%s,%d,%s\n", type.getSimpleName(), methodName, duration, (error == null ? "" : error.getClass().getSimpleName()));
    }
}
