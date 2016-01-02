package com.hmjf.web.filter

import org.springframework.boot.context.embedded.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order

/**
 * Created by jack on 16/1/1.
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
class WebFilterConfig {

    @Bean
    public FilterRegistrationBean registerLoginFilter() {
        LoginFilter filter = loginFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(1);
        List<String> urlPatterns = filter.urlPattern.split(';').toList()
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean registerAnonFilter() {
        AnonFilter filter = anonFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setOrder(0);
        registrationBean.setFilter(filter);
        List<String> urlPatterns = Arrays.asList(filter.urlPattern.split(';'));
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

    @Bean
    LoginFilter loginFilter(){
        return new LoginFilter();
    }

    @Bean
    AnonFilter anonFilter(){
        return new AnonFilter()
    }

}
