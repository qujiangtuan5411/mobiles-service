package com.sioo.point.config;

import com.sioo.point.properties.PointRedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/****
 * @description：自动装配类
 * @version：2.0.0
 * @author fanghuaiming
 * @data Created in 2019/10/25 3:46 PM
 *
 */
@Configuration
@ConditionalOnClass({GenerateRedisTemplate.class})
@EnableConfigurationProperties(PointRedisProperties.class)
@Order(1)
public class RedisAutoConfiguration {

    /**
     * 注入属性类
     */
    @Autowired
    private PointRedisProperties pointRedisProperties;

    /**
     * @Description: 在当前上下文中没有 GenerateRedisTemplate 类时创建类
     * @Param:
     * @return:
     * @Author: fanghuaiming
     * @Date: 3:29 PM 2019/10/24
     */
    @Bean
    @ConditionalOnMissingBean(GenerateRedisTemplate.class)
    public GenerateRedisTemplate createBean() {
        GenerateRedisTemplate redisConfig = new GenerateRedisTemplate();
        redisConfig.setDatabase(pointRedisProperties.getDatabase());
        redisConfig.setHostName(pointRedisProperties.getHostName());
        redisConfig.setMaxActive(pointRedisProperties.getMaxActive());
        redisConfig.setMaxIdle(pointRedisProperties.getMaxIdle());
        redisConfig.setMaxWait(pointRedisProperties.getMaxWait());
        redisConfig.setMinIdle(pointRedisProperties.getMinIdle());
        redisConfig.setPassword(pointRedisProperties.getPassword());
        redisConfig.setPort(pointRedisProperties.getPort());
        redisConfig.setTimeout(pointRedisProperties.getTimeout());
        return redisConfig;
    }
}
