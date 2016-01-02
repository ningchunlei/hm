package com.hmjf.web.filter

import com.hmjf.domain.SessionUser
import com.hmjf.service.SessionStore
import com.hmjf.token.TokenUtils
import com.hmjf.web.utils.CookieUtils
import com.hmjf.web.utils.LoggerHelp
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

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
class LoginFilter implements Filter{

    @Value('${web.login.filter:/u/*}')
    String urlPattern;

    @Value('${web.login.url:/login}')
    String loginUrl;

    @Autowired
    SessionStore sessionStore;

    static Logger logger = LoggerFactory.getLogger(LoginFilter)

    @Override
    void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        def cookies = CookieUtils.cookies(request);
        def utoken = cookies.get(TokenUtils.UTOKEN)
        if(utoken == null){
            response.sendRedirect(loginUrl);
            return;
        }
        SessionUser sessionUser = sessionStore.sessionUser(utoken)
        if(sessionUser == null){
            response.sendRedirect(loginUrl);
            return;
        }

        long uid = TokenUtils.uid(utoken);
        if(uid!=sessionUser.uid){
            logger.error("Warning!,SomeBody pretend to be uid:{} by uid:{},token:{}",sessionUser.uid,uid,utoken)
            response.sendRedirect(loginUrl);
            return;
        }
        LoggerHelp.initUserID(uid)

        TokenUtils.storeSessionUser(sessionUser)
        try{
            filterChain.doFilter(servletRequest, servletResponse);
        }finally {
            TokenUtils.clearSession()
        }
    }

    @Override
    void destroy() {

    }
}
