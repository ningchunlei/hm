package com.hmjf.web.filter

import com.hmjf.token.TokenUtils
import com.hmjf.web.utils.CookieUtils
import com.hmjf.web.utils.LoggerHelp
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by jack on 16/1/1.
 */
@Component
class AnonFilter implements Filter{

    @Value('${web.anon.filter:/*}')
    String urlPattern;

    static Logger logger = LoggerFactory.getLogger(AnonFilter)

    @Override
    void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        def cookies = CookieUtils.cookies(request);
        def dtoken = cookies.get(TokenUtils.DTOKEN)
        if(dtoken == null){
            dtoken = TokenUtils.dToken()
            def ck = [:]
            ck.put(TokenUtils.DTOKEN,dtoken)
            CookieUtils.addCookieInPermanent(response,ck)
        }
        LoggerHelp.initMDC(dtoken);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    void destroy() {

    }
}
