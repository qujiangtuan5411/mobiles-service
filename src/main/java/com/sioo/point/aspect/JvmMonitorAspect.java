package com.sioo.point.aspect;

import com.sioo.point.annotation.JvmMonitor;
import com.sioo.point.bo.JvmInfo;
import com.sioo.point.utils.MonitorUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/****
 * @description：JVM监控切点类
 *
 * @author fanghuaiming
 * @data Created in 2019/6/27 4:53 PM
 *
 */
@Aspect
@Component
@Order(3)
public class JvmMonitorAspect {

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(JvmMonitorAspect.class);

    /**
     * 线程池
     */
    @SuppressWarnings("all")
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1 );

    /** 
    * @Description: JVM监控点 
    * @Param:
    * @return:  
    * @Author: fanghuaiming
    * @Date:  
    */
    @Pointcut("@annotation(com.sioo.point.annotation.JvmMonitor)")
    public void jvmMonitorAspect(){

    }

    /** 
    * @Description: JVM信息打印(方法执行之前)
    * @Param:
    * @return:
    * @Author: fanghuaiming
    * @Date:
    */
    @Before("jvmMonitorAspect()")
    public void doBefore(JoinPoint joinPoint){
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> params = getAnnotationParams(joinPoint);
                    jvmMonitor(Boolean.parseBoolean(params.get("isOpen")), params.get("critical"));
                }
                catch (ClassNotFoundException e) {
                    logger.error("【JVM监控】监控模块出错-异常详细信息", e);
                }
            }
        });
    }

    /** 
    * @Description: 启动JVM监控
    * @Param:  
    * @return:  
    * @Author: fanghuaiming
    * @Date:  
    */
    private void jvmMonitor(Boolean isOpen, String critical) {
        if (isOpen) {
            JvmInfo jvmInfo = MonitorUtil.initJvmInfo(isOpen, critical);
            logger.info(
                    "JVM性能监控信息--当前JVM空闲内存:{}; 当前JVM内存总量:{}; JVM试图使用的最大内存:{}; JVM实际可用内存:{}; 阈值:{}; JVM实际可用内存占有率:{}; 是否低于阈值:{};",
                    jvmInfo.getFreeMemory() / 1024 / 1024 + "M", jvmInfo.getTotalMemory() / 1024 / 1024 + "M",
                    jvmInfo.getMaxMemory() / 1024 / 1024 + "M", jvmInfo.getActualAvailableMemory() / 1024 / 1024 + "M",
                    jvmInfo.getCritical(), jvmInfo.getMemmoryPercent(), jvmInfo.getJudgeValue());
        }
    }

    /** 
    * @Description: 获取注解参数
    * @Param:  
    * @return:  
    * @Author: fanghuaiming
    * @Date:  
    */
    private static Map<String, String> getAnnotationParams(JoinPoint joinPoint) throws ClassNotFoundException {
        Map<String, String> params = new HashMap<String, String>();
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String isOpen = "";
        String critical = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    isOpen = method.getAnnotation(JvmMonitor.class).isOpen();
                    critical = method.getAnnotation(JvmMonitor.class).critical();
                    break;
                }
            }
        }
        params.put("isOpen", isOpen);
        params.put("critical", critical);
        return params;
    }
}