package org.bongiorno.common.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class NullAdvice {

    public Object doAroundAdvice(ProceedingJoinPoint jp) throws Throwable {
        return jp.proceed();
    }
}
