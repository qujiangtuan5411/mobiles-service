package com.sioo.aspect;

import com.sioo.annotation.CustomLog;
import com.sioo.bo.CustomLogInfo;
import com.sioo.bo.LogInfo;
import com.sioo.utils.DateStyle;
import com.sioo.utils.DateUtil;
import com.sioo.utils.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/****
 * @description：自定义日志切点类
 *
 * @author fanghuaiming
 * @data Created in 2019/6/27 7:12 PM
 *
 */
@Aspect
@Component
@Order(1)
public class CustomLogAspect {

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(CustomLogAspect.class);

    /**
     * 线程池
     */
    @SuppressWarnings("all")
    private final ExecutorService fixedThreadPool = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    /** 
    * @Description: 自定义日志切点
    * @Param:
    * @return:
    * @Author: fanghuaiming
    * @Date: 7:13 PM 2019/6/27
    */
    @Pointcut("@annotation(com.sioo.annotation.CustomLog)")
    public void customAspect() {
    }


    /** 
    * @Description: 自定义日志处理 
    * @Param:  
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:13 PM 2019/6/27
    */
    @After("customAspect()")
    public void doAfter(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                customThread(joinPoint, request);
            }
        });
    }

    /** 
    * @Description: 自定义日志处理——线程内执行方法 
    * @Param:
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:14 PM 2019/6/27
    */
    private void customThread(JoinPoint joinPoint, HttpServletRequest request) {
        try {
            //自定义日志参数
            String info = "";
            if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                for (int i = 0; i < joinPoint.getArgs().length; i++) {
                    Object obj = joinPoint.getArgs()[i];
                    if (obj instanceof CustomLogInfo) {
                        info += JsonUtil.toJsonString(obj) + ";";
                    }
                }
            }
            LogInfo log = new LogInfo();
            log.setDescription(getCustomMethodDescription(joinPoint));
            log.setMethod(
                    (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            log.setCreateDate(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN));
            logger.info("=====自定义日志通知开始=====");
            logger.info("信息描述:{}; 时间:{}; 对应方法:{}; 自定义日志摘要:{}; ", log.getDescription(), log.getCreateDate(),
                    log.getMethod(), info);
            logger.info("=====自定义日志通知结束=====");
        }
        catch (Exception e) {
            //记录本地异常日志
            logger.error("【日志监控】自定义日志通知异常-异常详细信息", e);
        }
    }

    /** 
    * @Description: 获取注解中对方法的描述信息 用于自定义日志注解 
    * @Param:
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:15 PM 2019/6/27
    */
    private static String getCustomMethodDescription(JoinPoint joinPoint) throws ClassNotFoundException {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(CustomLog.class).description();
                    break;
                }
            }
        }
        return description;
    }
}
