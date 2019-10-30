package com.sioo.point.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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

    @Bean
    @Order(3)
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisTemplate redisTemplate = createRedisTemplate(lettuceConnectionFactory);
        return redisTemplate;
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
        return createRedisTemplate(lettuceConnectionFactory);
    }

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

}
