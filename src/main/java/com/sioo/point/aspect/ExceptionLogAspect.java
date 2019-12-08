package com.sioo.point.aspect;

import com.sioo.point.annotation.ExceptionLog;
import com.sioo.point.bo.LogInfo;
import com.sioo.point.utils.DateStyle;
import com.sioo.point.utils.DateUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
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
 * @description：错误日志捕获切点类
 *
 * @author fanghuaiming
 * @data Created in 2019/6/27 6:55 PM
 *
 */
@Aspect
@Component
@Order(1)
public class ExceptionLogAspect {

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ExceptionLogAspect.class);

    /**
     * 线程池
     */
    @SuppressWarnings("all")
    private final ExecutorService fixedThreadPool = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    /**
    * @Description: 异常日志的切点
    * @Param:
    * @return:
    * @Author: fanghuaiming
    * @Date: 6:57 PM 2019/6/27
    */
    @Pointcut("@annotation(com.sioo.point.annotation.ExceptionLog)")
    public void exceptionAspect() {
    }

    /** 
    * @Description: 异常通知（用于拦截和记录异常日志 
    * @Param:  
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:00 PM 2019/6/27
    */
    @AfterThrowing(pointcut = "exceptionAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                exceptionThread(joinPoint, e, request);
            }
        });
    }

    /** 
    * @Description: 异常日志处理——线程内执行方法 
    * @Param:
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:01 PM 2019/6/27
    */
    private void exceptionThread(JoinPoint joinPoint, Throwable e, HttpServletRequest request) {
        try {
            LogInfo log = new LogInfo();
            log.setDescription(getExceptionMethodDescription(joinPoint));
            log.setExceptionCode(e.getClass().getName());
            log.setExceptionDetail(e.getMessage());
            log.setMethod(
                    (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            log.setCreateDate(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN));
            logger.error("异常描述:{}; 时间:{}; 异常方法:{};  异常具体提示信息:{}", log.getDescription(), log.getCreateDate(),
                    log.getMethod(), e.getMessage(), e);
        }
        catch (Exception ex) {
            //记录本地异常日志
            logger.error("【日志监控】异常捕获出错-异常详细信息", ex);
        }
    }

    /** 
    * @Description: 获取注解中对方法的描述信息 用于错误日志注解  
    * @Param:
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:02 PM 2019/6/27
    */
    private static String getExceptionMethodDescription(JoinPoint joinPoint) throws ClassNotFoundException {
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
                    description = method.getAnnotation(ExceptionLog.class).description();
                    break;
                }
            }
        }
        return description;
    }
}
