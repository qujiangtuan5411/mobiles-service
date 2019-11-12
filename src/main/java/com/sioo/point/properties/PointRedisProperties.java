package com.sioo.point.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
* @Description: 配置类
* @Param:
* @return:
* @Author: fanghuaiming
* @Date: 3:10 PM 2019/10/24
*/
@Setter
@Getter
@ConfigurationProperties(prefix = "com.sioo.point.redis")
public class PointRedisProperties {

    private static final int DEFAULT_DATABASE =16 ;
    private static final long DEFAULT_TIMEOUT= 5000;
    private static final int DEFAULT_MAXACTIVE = 8;
    private static final  int DEFAULT_MAXWAIT =-1;
    private static final  int DEFAULT_MAXIDLE = 13;
    private static final  int DEFAULT_MINIDLE = 5;
    private static final  String DEFAULT_HOSTNAME = "172.19.84.241";
    private static final  int DEFAULT_PORT = 6379;
    private static final  String DEFAULT_PASSWORD = "SIOO123!@#";

    /**
     * 切换使用的库
     */
    private  int database = DEFAULT_DATABASE;

    /**
     * 连接超时时间
     */
    private  long timeout = DEFAULT_TIMEOUT;

    /**
     * 最大存活时间
     */
    private  int maxActive = DEFAULT_MAXACTIVE;

    /**
     * 最大等待的时间
     */
    private int maxWait = DEFAULT_MAXWAIT;

    /**
     * 最大连接数
     */
    private int maxIdle = DEFAULT_MAXIDLE;

    /**
     * 最小连接数
     */
    private int minIdle = DEFAULT_MINIDLE;

    /**
     * 连接host（ip）
     */
    private String hostName = DEFAULT_HOSTNAME;

    /**
     * 端口port
     */
    private int port = DEFAULT_PORT;

    /**
     * 密码
     */
    private String password = DEFAULT_PASSWORD;

}