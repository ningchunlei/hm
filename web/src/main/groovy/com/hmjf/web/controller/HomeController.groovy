package com.hmjf.web.controller

import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Created by jack on 16/1/2.
 */
@Component
@RequestMapping("/")
class HomeController{

    @RequestMapping("login")
    def String login(){
        return "/user/login"
    }

}
