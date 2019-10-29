package com.sioo.point.config;

import com.sioo.point.properties.SlaveRedisProperties;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/****
 * @description：自动装配类
 * @version：2.0.0
 * @author fanghuaiming
 * @data Created in 2019/10/25 3:46 PM
 *
 */
@Configuration
@ConditionalOnClass({GenerateRedisTemplateOri.class})
@EnableConfigurationProperties(SlaveRedisProperties.class)
@Order(1)
public class SlaveRedisAutoConfiguration {

    /**
     * 注入属性类
     */
    @Autowired
    private SlaveRedisProperties slaveRedisProperties;

    @SuppressWarnings("all")
    @Bean
    @Order(2)
    @ConditionalOnMissingBean(SlaveRedisAutoConfiguration.class)
    public RedisTemplate slaveRedisTemplate() {
        GenerateRedisTemplateOri redisConfig = new GenerateRedisTemplateOri();
        redisConfig.setMaxIdle(slaveRedisProperties.getMaxIdle());
        redisConfig.setMinIdle(slaveRedisProperties.getMinIdle());
        redisConfig.setMaxActive(slaveRedisProperties.getMaxActive());
        redisConfig.setMaxWait(slaveRedisProperties.getMaxWait());
        GenericObjectPoolConfig genericObjectPoolConfig = redisConfig.genericObjectPoolConfig();
        redisConfig.setDatabase(slaveRedisProperties.getDatabase());
        redisConfig.setHostName(slaveRedisProperties.getHostName());
        redisConfig.setPassword(slaveRedisProperties.getPassword());
        redisConfig.setPort(slaveRedisProperties.getPort());
        redisConfig.setTimeout(slaveRedisProperties.getTimeout());
        LettuceConnectionFactory lettuceConnectionFactory = redisConfig.lettuceConnectionFactory(genericObjectPoolConfig);
        return redisConfig.createRedisTemplate(lettuceConnectionFactory);
    }

}
