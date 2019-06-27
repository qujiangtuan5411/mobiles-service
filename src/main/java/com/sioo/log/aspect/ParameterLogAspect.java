package com.sioo.log.aspect;

import com.sioo.log.annotation.ParameterLog;
import com.sioo.log.bo.CustomLogInfo;
import com.sioo.log.bo.LogInfo;
import com.sioo.log.utils.DateStyle;
import com.sioo.log.utils.DateUtil;
import com.sioo.log.utils.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/****
 * @description：参数日志切点类
 *
 * @author fanghuaiming
 * @data Created in 2019/6/27 7:04 PM
 *
 */
@Aspect
@Component
public class ParameterLogAspect {

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ParameterLogAspect.class);

    /**
     * 线程池
     */
    @SuppressWarnings("all")
    private final ExecutorService fixedThreadPool = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    /**
    * @Description: 参数日志切点
    * @Param:
    * @return:
    * @Author: fanghuaiming
    * @Date: 7:05 PM 2019/6/27
    */
    @Pointcut("@annotation(com.sioo.log.annotation.ParameterLog)")
    public void parameterAspect() {
    }

    /** 
    * @Description: 前置通知（用于拦截Controller层-记录入参和用户的操作） 
    * @Param:  
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:06 PM 2019/6/27
    */
    @Before("parameterAspect()")
    public void doBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                parameterThread(joinPoint, request);
            }
        });
    }

    /** 
    * @Description: 参数日志处理——线程内执行方法 
    * @Param:
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:07 PM 2019/6/27
    */
    private void parameterThread(JoinPoint joinPoint, HttpServletRequest request) {
        try {
            //请求参数
            String params = "";
            if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                for (int i = 0; i < joinPoint.getArgs().length; i++) {
                    Object obj = joinPoint.getArgs()[i];
                    if (obj instanceof BindingResult) {
                        params += "valid bindingResult;";
                    }
                    // 排除自定义日志信息
                    else if (obj instanceof CustomLogInfo) {
                        continue;
                    }
                    else {
                        params += JsonUtil.toJsonString(obj) + ";";
                    }
                }
            }
            logger.info("=====前置通知开始=====");
            LogInfo log = new LogInfo();
            log.setDescription(getParameterMethodDescription(joinPoint));
            log.setMethod(
                    (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            log.setCreateDate(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS_CN));
            log.setParams(params);
            logger.info("前置日志信息:{}", JsonUtil.toJsonString(log));
            logger.info("=====前置通知结束=====");
        }
        catch (Exception e) {
            //记录本地异常日志
            logger.error("【日志监控】前置通知异常-异常详细信息", e);
        }
    }

    /**
    * @Description: 获取注解中对方法的描述信息 用于参数注解
    * @Param:
    * @return:
    * @Author: fanghuaiming
    * @Date: 7:09 PM 2019/6/27
    */
    private static String getParameterMethodDescription(JoinPoint joinPoint) throws ClassNotFoundException {
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
                    description = method.getAnnotation(ParameterLog.class).description();
                    break;
                }
            }
        }
        return description;
    }
}
