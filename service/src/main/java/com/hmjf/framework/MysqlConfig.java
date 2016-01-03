package com.hmjf.framework;

import com.github.pagehelper.PageHelper;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="mysql")
@ConditionalOnExpression("${mysql.enable:false}")
@MapperScan(basePackages = "com.hmjf", sqlSessionFactoryRef = "sessionFactory", annotationClass = MyBatisRepository.class)
public class MysqlConfig {

	Logger logger = LoggerFactory.getLogger(MysqlConfig.class);

    private String url;
    private String driverClassName;
    private String username;
    private String password;

	private int initialSize;
    private int maxActive;
    private int maxIdle;
    @Value("${minIdle:0}")
    private int minIdle;
    @Value("${minWait:1000}")
    private int maxWait;
    @Value("${defaultAutoCommit:true}")
    private boolean defaultAutoCommit;
    private String validationQuery;

    @Value("${validationInterval:600000}")
    private long validationInterval;

    @Value("${testWhileIdle:true}")
    private boolean testWhileIdle;
    @Value("${logAbandoned:true}")
    private boolean logAbandoned;
    @Value("${removeAbandoned:true}")
    private boolean removeAbandoned;
    @Value("${removeAbandonedTimeout:300}")
    private int removeAbandonedTimeout;
    
    @Bean(destroyMethod="close")
    public DataSource dataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(maxActive);
		dataSource.setInitialSize(initialSize);
        dataSource.setMaxIdle(maxIdle);	// 连接池最大连接数量
        dataSource.setMinIdle(minIdle);	// 连接池最小空闲
        dataSource.setMaxWait(maxWait);
        dataSource.setDefaultAutoCommit(defaultAutoCommit);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setValidationInterval(validationInterval);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setLogAbandoned(logAbandoned);
        dataSource.setRemoveAbandoned(removeAbandoned);
        dataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

	@Bean
	public SqlSessionFactoryBean sessionFactory() throws Exception {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><!DOCTYPE configuration\n" +
				"  PUBLIC \"-//mybatis.org//DTD Config 3.0//EN\"\n" +
				"  \"http://mybatis.org/dtd/mybatis-3-config.dtd\"><configuration><settings>\n" +
				"  <setting name=\"mapUnderscoreToCamelCase\" value=\"true\"/></settings></configuration>";
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setConfigLocation(new InputStreamResource(new ByteArrayInputStream(xml.getBytes())));

		return sessionFactory;
	}

    //@Bean
//    public DataSourceTransactionManager transactionManager() {
//    	return new DataSourceTransactionManager(dataSource());
//    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
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

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public boolean isDefaultAutoCommit() {
		return defaultAutoCommit;
	}

	public void setDefaultAutoCommit(boolean defaultAutoCommit) {
		this.defaultAutoCommit = defaultAutoCommit;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public long getValidationInterval() {
		return validationInterval;
	}

	public void setValidationInterval(long validationInterval) {
		this.validationInterval = validationInterval;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public boolean isLogAbandoned() {
		return logAbandoned;
	}

	public void setLogAbandoned(boolean logAbandoned) {
		this.logAbandoned = logAbandoned;
	}

	public boolean isRemoveAbandoned() {
		return removeAbandoned;
	}

	public void setRemoveAbandoned(boolean removeAbandoned) {
		this.removeAbandoned = removeAbandoned;
	}

	public int getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}

	public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
		this.removeAbandonedTimeout = removeAbandonedTimeout;
	}
}
