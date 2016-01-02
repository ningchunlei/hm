package com.hmjf.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.function.Function;

@Component
public class RedisManager {

    @Resource
    private JedisPool pool;

    private JedisCommands proxy;

    public JedisCommands jedisCommands(){
        if(proxy!=null){
            return proxy;
        }
        proxy = (JedisCommands) Proxy.newProxyInstance(Jedis.class.getClassLoader(), new Class[] { JedisCommands.class },
                new RedisCommandProxy(pool));
        return proxy;
    }

}
