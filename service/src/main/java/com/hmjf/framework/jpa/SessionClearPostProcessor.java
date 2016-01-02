package com.hmjf.framework.jpa;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.support.RepositoryProxyPostProcessor;

/**
 * Created by ningcl on 15/9/6.
 */
public class SessionClearPostProcessor implements RepositoryProxyPostProcessor {

    public static final SessionClearPostProcessor INSTANCE = new SessionClearPostProcessor();

    @Override
    public void postProcess(ProxyFactory factory, RepositoryInformation repositoryInformation) {
        factory.addAdvice(0,SessionClearInterceptor.INSTANCE);
    }

}
