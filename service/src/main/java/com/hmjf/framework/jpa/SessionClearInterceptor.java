package com.hmjf.framework.jpa;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.Ordered;

import java.io.Serializable;

/**
 * Created by ningcl on 15/9/6.
 */
public class SessionClearInterceptor implements MethodInterceptor, Ordered, Serializable {

    public static final SessionClearInterceptor INSTANCE = new SessionClearInterceptor();

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object ret = methodInvocation.proceed();
        if(methodInvocation.getThis() instanceof  BaseRepositoryImpl){
            ((BaseRepositoryImpl)methodInvocation.getThis()).entityManager.clear();
        }
        return ret;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
