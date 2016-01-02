package com.hmjf.web.utils

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by jack on 16/1/2.
 */
class CookieUtils {

    def static HashMap<String,String> cookies(HttpServletRequest httpServletRequest){
        HashMap<String,String> map = [:]
        httpServletRequest.cookies.each {
            map.put(it.name,it.value)
        }
        return map;
    }


    def static void addCookieInSession(HttpServletResponse httpServletResponse,HashMap<String,String> map){
        addCookie(httpServletResponse,map,-1)
    }

    def static void deleteCookie(HttpServletResponse httpServletResponse,HashMap<String,String> map){
        addCookie(httpServletResponse,map,0)
    }

    def static void addCookieInPermanent(HttpServletResponse httpServletResponse,HashMap<String,String> map){
        addCookie(httpServletResponse,map,Integer.MAX_VALUE)
    }

    def static void addCookie(HttpServletResponse httpServletResponse,HashMap<String,String> map,int expire){
        map.each {
            k,v ->
                Cookie cookie = new Cookie(k,v)
                cookie.path = "/";
                cookie.setMaxAge(expire);
                httpServletResponse.addCookie(cookie)
        }
    }

}
