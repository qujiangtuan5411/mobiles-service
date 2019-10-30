package com.sioo.point.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.util.ObjectUtils;

import java.time.Duration;

/**
 * 原生redisTemplate
 *
 * @author seer
 * @date 2018/5/30 09:32
 */
@Getter
@Setter
@Order(2)
public class GenerateRedisTemplateOri extends CachingConfigurerSupport {

	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(GenerateRedisTemplateOri.class);

	private int database;

	private long timeout;

	private int maxActive;

	private int maxWait;

	private int maxIdle;

	private int minIdle;

	private String hostName;

	private int port;

	private String password;

	/**
	* @Description: 创建connectionFactory
	* @Param:
	* @return:
	* @Author: fanghuaiming
	* @Date: 5:45 PM 2019/10/29
	*/
	LettuceConnectionFactory lettuceConnectionFactory(GenericObjectPoolConfig genericObjectPoolConfig) {
		// 单机版配置
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setDatabase(database);
		redisStandaloneConfiguration.setHostName(hostName);
		redisStandaloneConfiguration.setPort(port);
		if (!ObjectUtils.isEmpty(password)) {
			RedisPassword redisPassword = RedisPassword.of(password);
			redisStandaloneConfiguration.setPassword(redisPassword);
			logger.info("======您当前监控monitoring组件所使用redis配置=【ip:{}】【port:{}】【password:{}】【database:{}】======",hostName,port,password,database);
		}


		LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
				.commandTimeout(Duration.ofMillis(timeout))
				.poolConfig(genericObjectPoolConfig)
				.build();

		LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration,clientConfig);
		return factory;
	}

	/**
	* @Description: 创建pool
	* @Param:
	* @return:
	* @Author: fanghuaiming
	* @Date: 5:46 PM 2019/10/29
	*/
	public GenericObjectPoolConfig genericObjectPoolConfig() {
		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
		genericObjectPoolConfig.setMaxIdle(maxIdle);
		genericObjectPoolConfig.setMinIdle(minIdle);
		genericObjectPoolConfig.setMaxTotal(maxActive);
		genericObjectPoolConfig.setMaxWaitMillis(maxWait);
		return genericObjectPoolConfig;
	}
	/**
	 * 默认配置 redis template
	 *
	 * @param redisConnectionFactory
	 * @return
	 */
	/*@Bean
	public RedisTemplate redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
		return createRedisTemplate(redisConnectionFactory);
	}*/

}
