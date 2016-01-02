package com.hmjf.web.controller

import com.hmjf.domain.Result
import com.hmjf.domain.ResultCode
import com.hmjf.domain.SessionUser
import com.hmjf.entity.User
import com.hmjf.service.SessionStore
import com.hmjf.service.UserService
import com.hmjf.token.TokenUtils
import com.hmjf.utils.StringUtils
import com.hmjf.utils.Utils
import com.hmjf.web.utils.CookieUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by jack on 16/1/2.
 */
@Controller
class UserController {

    @Autowired
    UserService userService

    @Autowired
    SessionStore sessionStore

    @RequestMapping(value="/v1/api/user/register",method = RequestMethod.POST)
    @ResponseBody
    def Result register(User user, HttpServletRequest request){
        if(StringUtils.isEmpty(user.name) || StringUtils.isEmpty(user.password)){
            return new Result(code:ResultCode.ParameterVaildatorError,message:"用户名密码不能为空!");
        }
        user.registerIp = Utils.getRemoteHost(request)
        try {
            userService.registerUser(user)
        }catch (Exception e){
            return new Result(code:ResultCode.ERROR,message: "用户已存在!")
        }
        return new Result(code:ResultCode.OK,message: "注册成功")
    }

    @RequestMapping(value="/v1/api/user/login",method = RequestMethod.POST)
    @ResponseBody
    def Result login(User user,HttpServletResponse response){
        if(StringUtils.isEmpty(user.name) || StringUtils.isEmpty(user.password)){
            return new Result(code:ResultCode.ParameterVaildatorError,message:"用户名密码不能为空!");
        }
        user = userService.login(user)
        if(user!=null){
            String token = TokenUtils.uToken(user.id);
            String utoken = TokenUtils.UTOKEN;
            CookieUtils.addCookieInSession(response,[utoken:token])
            sessionStore.storeSession(new SessionUser(uid:user.id,utoken:token));
            return new Result(code:ResultCode.OK,message:" 登陆成功!");
        }else{
            return new Result(code:ResultCode.ERROR,message:"用户名密码错误!");
        }
    }

    @RequestMapping(value="/u/v1/api/user/logout")
    @ResponseBody
    def Result logout(HttpServletResponse response){
        CookieUtils.deleteCookie(response,[utoken:""])
        sessionStore.clearSession(TokenUtils.sessionUser().utoken)
        return new Result(code:ResultCode.OK,message:"");
    }

    @RequestMapping('/u/user/info')
    def ModelAndView info(){
        ModelAndView modelAndView = new ModelAndView("/user/info")
        modelAndView.addObject("user",userService.user(TokenUtils.sessionUser().uid));
        return modelAndView;
    }

}
