package com.hmjf.service;

import com.alibaba.fastjson.JSON;
import com.hmjf.domain.SessionUser;
import com.hmjf.framework.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jack on 16/1/2.
 */
@Service
public class SessionStore {

    @Autowired
    RedisManager redisManager;

    public void storeSession(SessionUser sessionUser){
        redisManager.jedisCommands().set(sessionUser.utoken, JSON.toJSONString(sessionUser));
    }

    public SessionUser sessionUser(String token){
        String tmp = redisManager.jedisCommands().get(token);
        if(tmp==null){
            return null;
        }
        return JSON.parseObject(tmp, SessionUser.class);
    }

    public void clearSession(String token){
        redisManager.jedisCommands().del(token);
    }
}
