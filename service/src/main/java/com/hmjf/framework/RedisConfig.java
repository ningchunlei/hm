package com.hmjf.framework;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="redis")
@ConditionalOnExpression("${redis.enable:false}")
public class RedisConfig {
	private int database = 0;
	private String host;
	private String password;
	private int port;
	
	private int maxIdle = 8;
	private int minIdle = 0;
	private int maxActive = 8;
	private int maxWait = -1;
	
	private String sentinelMaster;
	private String sentinelNodes;
	
	@Bean
	public JedisConnectionFactory redisConnectionFactory() throws UnknownHostException {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(this.getMaxActive());
		config.setMaxIdle(this.getMaxIdle());
		config.setMinIdle(this.getMinIdle());
		config.setMaxWaitMillis(this.getMaxWait());

		JedisConnectionFactory factory = new JedisConnectionFactory(config);
		factory.setHostName(this.getHost());
		factory.setPort(this.getPort());
		if (this.getPassword() != null) {
			factory.setPassword(this.getPassword());
		}
		factory.setDatabase(this.getDatabase());
		return factory;
	}

    @Bean
    public JedisPool jedisPool() {
        try {
            JedisPool pool = new JedisPool(redisConnectionFactory().getPoolConfig(), host, port,200,password);
            return pool;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }


	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public String getSentinelMaster() {
		return sentinelMaster;
	}

	public void setSentinelMaster(String sentinelMaster) {
		this.sentinelMaster = sentinelMaster;
	}

	public String getSentinelNodes() {
		return sentinelNodes;
	}

	public void setSentinelNodes(String sentinelNodes) {
		this.sentinelNodes = sentinelNodes;
	}
	
}
