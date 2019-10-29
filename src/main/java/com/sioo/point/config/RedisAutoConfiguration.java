package com.sioo.point.config;

import com.sioo.point.properties.PointRedisProperties;
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
@EnableConfigurationProperties({PointRedisProperties.class, SlaveRedisProperties.class})
@Order(1)
public class RedisAutoConfiguration {

    /**
     * 注入属性类
     */
    @Autowired
    private PointRedisProperties pointRedisProperties;

    /**
     * 注入属性
     */
    @Autowired
    private SlaveRedisProperties slaveRedisProperties;

    /**
    * @Description: 创建默认的pool
    * @Param:
    * @return:
    * @Author: fanghuaiming
    * @Date: 5:43 PM 2019/10/29
    */
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

    /**
    * @Description: 创建默认的connectionFactory
    * @Param:
    * @return:
    * @Author: fanghuaiming
    * @Date: 5:44 PM 2019/10/29
    */
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

    /**
    * @Description: 创建一个新的slaveRedisTemplate
    * @Param:
    * @return:
    * @Author: fanghuaiming
    * @Date: 5:44 PM 2019/10/29
    */
    @Bean
    @Order(2)
    @ConditionalOnMissingBean(GenerateRedisTemplateOri.class)
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
