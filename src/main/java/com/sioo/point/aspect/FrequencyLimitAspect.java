package com.sioo.point.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sioo.point.annotation.FrequencyLimit;
import com.sioo.point.utils.DateUtil;
import com.sioo.point.utils.MonitoringRedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/****
 * @description：限制方法调用次数
 *
 * @author liuquan
 * @data Created in 2019/09/09 15:20
 *
 */
@Aspect
@Component
@Order(1)
public class FrequencyLimitAspect {

    @Autowired
    private MonitoringRedisUtil monitoringRedisUtil;

    /**
    * @Description: aop切点的区域 
    * @Param:
    * @return:  
    * @Author: liuquan
    * @Date: 2019/09/09 15:20
    */
    @Pointcut("@annotation(com.sioo.point.annotation.FrequencyLimit)")
    public void annotationPointCut() {
    }

    /**
    * @Description: 对切中的方式的具体的处理
    * @Param:
    * @return:  
    * @Author: liuquan
    * @Date: 2019/09/09 15:20
    */
    @Around("annotationPointCut()")
    public Object frequencyBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = request.getRequestURI();
        String key = uri.substring(uri.lastIndexOf("/")+1);
        //往redis里面原子自增，date_user: frequency，判断结果是否超出入参
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        FrequencyLimit action = method.getAnnotation(FrequencyLimit.class);
        long frequency = action.frequency();
        String date = DateUtil.DateToString(new Date(), "yyyyMMdd");
        Object[] args = joinPoint.getArgs();
        Integer userName;
        if("smsAccountgetChildId".equals(key)){
            userName = Integer.parseInt(args[0].toString());
        }else{
            userName = JSON.parseObject(JSONObject.toJSONString(args[0])).getInteger("uid");
        }
        long frequencyAfter = monitoringRedisUtil.incr(key+"_"+date+"_"+userName, 1);

        // 如果该key不存在，则从0开始计算，并且当frequencyAfter为1的时候，设置过期时间,过期时间为秒单位
        if (frequencyAfter == 1) {
            monitoringRedisUtil.expire(key+"_"+date+"_"+userName, action.timeSecond());
        }
        //超过设定的频率
        if(frequencyAfter>frequency){
            throw new Exception("exceeds frequency limit");
        }
        return joinPoint.proceed(args);
    }

}
