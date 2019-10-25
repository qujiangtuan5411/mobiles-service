package com.sioo.point.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ObjectUtils;

import java.time.Duration;

/**
 * GenerateRedisTemplate
 *
 * @author seer
 * @date 2018/5/30 09:32
 */
@Getter
@Setter
@Order(2)
public class GenerateRedisTemplate extends CachingConfigurerSupport {

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
	 * 本地数据源 redis template
	 *
	 * @return
	 */
	public RedisTemplate generateBatchIdRedisTemplate() {
		/* ========= 基本配置 ========= */
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(hostName);
		configuration.setPort(port);
		configuration.setDatabase(database);
		if (!ObjectUtils.isEmpty(password)) {
			RedisPassword redisPassword = RedisPassword.of(password);
			configuration.setPassword(redisPassword);
		}

		/* ========= 连接池通用配置 ========= */
		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
		genericObjectPoolConfig.setMaxTotal(maxActive);
		genericObjectPoolConfig.setMinIdle(minIdle);
		genericObjectPoolConfig.setMaxIdle(maxIdle);
		genericObjectPoolConfig.setMaxWaitMillis(maxWait);

		/* ========= lettuce pool ========= */
		LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
		builder.poolConfig(genericObjectPoolConfig);
		builder.commandTimeout(Duration.ofSeconds(timeout));
		LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration, builder.build());
		connectionFactory.afterPropertiesSet();

		/* ========= 创建 template ========= */
		return createRedisTemplate(connectionFactory);
	}

	/**
	 * 默认配置 redis template
	 *
	 * @param redisConnectionFactory
	 * @return
	 */
	/*@Bean
	public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		return createRedisTemplate(redisConnectionFactory);
	}*/

	/**
	 * json 实现 redisTemplate
	 * <p>
	 * 该方法不能加 @Bean 否则不管如何调用，connectionFactory都会是默认配置
	 *
	 * @param redisConnectionFactory
	 * @return
	 */
	@SuppressWarnings("all")
	public RedisTemplate createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		// 配置连接工厂
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		//使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		// 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		// 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		// 值采用json序列化
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		//使用StringRedisSerializer来序列化和反序列化redis的key值
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		// 设置hash key 和value序列化模式
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	/**
	 * @Description: 对hash类型的数据操作
	 * @Param:
	 * @return:
	 * @Author: fanghuaiming
	 * @Date: 1:24 PM 2019/8/2
	 */
	@Bean
	public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForHash();
	}

	/**
	 * @Description: 对redis字符串类型数据操作
	 * @Param:
	 * @return:
	 * @Author: fanghuaiming
	 * @Date: 1:24 PM 2019/8/2
	 */
	@Bean
	public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForValue();
	}

	/**
	 * @Description: 对链表类型的数据操作
	 * @Param:
	 * @return:
	 * @Author: fanghuaiming
	 * @Date: 1:25 PM 2019/8/2
	 */
	@Bean
	public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForList();
	}

	/**
	 * @Description: 对无序集合类型的数据操作
	 * @Param:
	 * @return:
	 * @Author: fanghuaiming
	 * @Date: 1:25 PM 2019/8/2
	 */
	@Bean
	public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForSet();
	}

	/**
	 * @Description: 对有序集合类型的数据操作
	 * @Param:
	 * @return:
	 * @Author: fanghuaiming
	 * @Date: 1:25 PM 2019/8/2
	 */
	@Bean
	public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForZSet();
	}

}
