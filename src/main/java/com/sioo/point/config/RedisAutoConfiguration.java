package com.sioo.point.config;

import com.sioo.point.properties.PointRedisProperties;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/****
 * @description：自动装配类
 * @version：2.0.0
 * @author fanghuaiming
 * @data Created in 2019/10/25 3:46 PM
 *
 */
@Configuration
@ConditionalOnClass({GenerateRedisTemplateOri.class})
@EnableConfigurationProperties(PointRedisProperties.class)
@Order(1)
public class RedisAutoConfiguration {

    /**
     * 注入属性类
     */
    @Autowired
    private PointRedisProperties pointRedisProperties;

    @Bean
    @Order(1)
    @ConditionalOnMissingBean(GenerateRedisTemplateOri.class)
    public GenericObjectPoolConfig genericObjectPoolConfig() {
        GenerateRedisTemplateOri redisConfig = new GenerateRedisTemplateOri();
        redisConfig.setMaxIdle(pointRedisProperties.getMaxIdle());
        redisConfig.setMinIdle(pointRedisProperties.getMinIdle());
        redisConfig.setMaxActive(pointRedisProperties.getMaxActive());
        redisConfig.setMaxWait(pointRedisProperties.getMaxWait());
        return redisConfig.genericObjectPoolConfig();
    }

    @Bean
    @Order(2)
    @ConditionalOnMissingBean(GenerateRedisTemplateOri.class)
    public LettuceConnectionFactory lettuceConnectionFactory(GenericObjectPoolConfig genericObjectPoolConfig) {
        GenerateRedisTemplateOri redisConfig = new GenerateRedisTemplateOri();
        redisConfig.setDatabase(pointRedisProperties.getDatabase());
        redisConfig.setHostName(pointRedisProperties.getHostName());
        redisConfig.setPassword(pointRedisProperties.getPassword());
        redisConfig.setPort(pointRedisProperties.getPort());
        redisConfig.setTimeout(pointRedisProperties.getTimeout());
        return redisConfig.lettuceConnectionFactory(genericObjectPoolConfig);
    }

}
