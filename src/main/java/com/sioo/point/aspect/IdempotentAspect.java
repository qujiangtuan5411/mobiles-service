package com.sioo.point.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sioo.point.annotation.Idempotent;
import com.sioo.point.utils.JsonUtil;
import com.sioo.point.utils.MD5Utils;
import com.sioo.point.utils.MonitoringRedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/****
 * @description：幂等性通知切面
 * @version：1.0.0
 * @author fanghuaiming
 * @data Created in 2019/10/17 5:23 PM
 *
 */
@Aspect
@Component
@Order(1)
public class IdempotentAspect {

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(IdempotentAspect.class);

    @Autowired
    private MonitoringRedisUtil monitoringRedisUtil;

    /**
     * @Description: 幂等性切点
     * @Param:
     * @return:
     * @Author: fanghuaiming
     * @Date: 7:05 PM 2019/6/27
     */
    @Pointcut("@annotation(com.sioo.point.annotation.Idempotent)")
    public void idempotentAspect() {
    }

    @Around("idempotentAspect()")
    public Object idempotentAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Idempotent annotation = method.getAnnotation(Idempotent.class);
        String description = annotation.description();
        long expire = annotation.expire();
        if(!description.equals("")){
            logger.info("=====幂等性:{}=====", description);
        }
        // 定义返回对象、得到方法需要的参数
        Object obj = null;
        Object[] args = joinPoint.getArgs();
        if (null != args && joinPoint.getArgs().length > 0) {
            Object[] objects = joinPoint.getArgs();
            Object object = objects[0];
            String idempotent = JsonUtil.toJsonString(object);
            JSONObject jsonObject = JSON.parseObject(idempotent);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(jsonObject.getInteger("userTemplateId").toString())
                    .append(jsonObject.getString("messageName"))
                    .append(jsonObject.getString("mobiles"));
            String idempotentKey = MD5Utils.toMD5(stringBuilder.toString());
            long incr = monitoringRedisUtil.incr(jsonObject.getInteger("userTemplateId").toString()+"_"+idempotentKey, 1);
            // 如果该key不存在，则从0开始计算，并且当incr为1的时候，设置过期时间,过期时间为秒单位
            if (incr == 1) {
                monitoringRedisUtil.expire(jsonObject.getInteger("userTemplateId").toString()+"_"+idempotentKey, expire);
            }
            if(incr > 1){
                throw new Exception("idempotentException");
            }
            //执行请求
            obj = dealOldMethod(joinPoint, obj, args);
        }
        return obj;
    }

    /** 
    * @Description: 执行本来的方法 
    * @Param:  
    * @return:  
    * @Author: fanghuaiming
    * @Date: 5:30 PM 2019/10/17
    */
    private Object dealOldMethod(ProceedingJoinPoint joinPoint, Object obj,
                                 Object[] args) throws Throwable {
        obj = joinPoint.proceed(args);
        return obj;
    }
}
