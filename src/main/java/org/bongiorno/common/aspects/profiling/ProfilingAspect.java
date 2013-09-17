package org.bongiorno.common.aspects.profiling;

import org.bongiorno.common.aspects.profiling.actions.ProfilingAction;
import org.aspectj.lang.ProceedingJoinPoint;

public class ProfilingAspect {

    private ProfilingAction action;

    public ProfilingAspect(ProfilingAction action) {
        this.action = action;
    }

    public Object doAroundAdvice(ProceedingJoinPoint jp) throws Throwable{
        long startTime = System.currentTimeMillis();

        Object retVal = null;
        Throwable error = null;
        try{
            retVal = jp.proceed();
        }catch (Throwable t){
            error = t;
        }

        Class withinType = jp.getSourceLocation().getWithinType();
        String methodName = jp.getSignature().getName();

        long endTime = System.currentTimeMillis();
        long runningTime = endTime - startTime;

        action.perform(withinType, methodName, jp.getArgs(), runningTime, error);

        if( error != null ){
            throw error;
        }

        return retVal;
    }
}
