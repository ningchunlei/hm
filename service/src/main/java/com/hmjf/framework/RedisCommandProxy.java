package com.hmjf.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

/**
 * Created by user on 15/12/8.
 */
public class RedisCommandProxy  implements InvocationHandler {

    static Logger logger = LoggerFactory.getLogger(RedisCommandProxy.class);

    private JedisPool jedisPool;

    static HashMap<String,String> writeMap = new HashMap<>();

    static {
        writeMap.put("set","");
        writeMap.put("expire","");
        writeMap.put("pexpire","");
        writeMap.put("expireAt","");
        writeMap.put("pexpireAt","");
        writeMap.put("setbit","");
        writeMap.put("setrange","");
        writeMap.put("setnx","");
        writeMap.put("setex","");
        writeMap.put("decrBy","");
        writeMap.put("decr","");
        writeMap.put("incrBy","");
        writeMap.put("incrByFloat","");
        writeMap.put("incr","");
        writeMap.put("append","");
        writeMap.put("hset","");
        writeMap.put("hsetnx","");
        writeMap.put("hmset","");
        writeMap.put("hincrBy","");
        writeMap.put("hdel","");
        writeMap.put("rpush","");
        writeMap.put("lpush","");
        writeMap.put("lset","");
        writeMap.put("lrem","");
        writeMap.put("lpop","");
        writeMap.put("sadd","");
        writeMap.put("rpop","");
        writeMap.put("srem","");
        writeMap.put("spop","");
        writeMap.put("zadd","");
        writeMap.put("zrem","");
        writeMap.put("zincrby","");
        writeMap.put("linsert","");
        writeMap.put("lpushx","");
        writeMap.put("del","");
        writeMap.put("echo","");
        writeMap.put("move","");
        writeMap.put("pfadd","");


    }

    public RedisCommandProxy(JedisPool jedisPool){
        this.jedisPool = jedisPool;
    }


    public <T> T execute(Function<Jedis,T> function){
        Jedis jedis = null;
        T ret = null ;
        try {
            jedis = jedisPool.getResource();
            ret = function.apply(jedis);
        } catch (Throwable ex) {
            ex.printStackTrace();
            if(jedis != null) {
                logger.error("read redis error,host="+jedis.getClient().getSocket().toString(), ex);
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            }
        }
        return ret;
    }

    @Override
    public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
        String name = method.getName();
        Object obj = null;

        return execute(new Function<Jedis, Object>() {
            @Override
            public Object apply(Jedis jedis) {
                try {
                    return method.invoke(jedis, args);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
