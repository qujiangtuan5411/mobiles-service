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

    private  int database = DEFAULT_DATABASE;

    private  long timeout = DEFAULT_TIMEOUT;

    private  int maxActive = DEFAULT_MAXACTIVE;

    private int maxWait = DEFAULT_MAXWAIT;

    private int maxIdle = DEFAULT_MAXIDLE;

    private int minIdle = DEFAULT_MINIDLE;

    private String hostName = DEFAULT_HOSTNAME;

    private int port = DEFAULT_PORT;

    private String password = DEFAULT_PASSWORD;

}