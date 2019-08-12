package com.sioo.point.aspect;

import com.sioo.point.annotation.ShowTime;
import com.sioo.point.bo.MethodEntity;
import com.sioo.point.utils.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/****
 * @description：统计每个方法的次数
 *
 * @author fanghuaiming
 * @data Created in 2019/6/27 7:20 PM
 *
 */
@Aspect
@Component
@Order(1)
public class ShowTimeAspect {

    /**
     * 日志对象
     */
    private final static Log logger = LogFactory
            .getLog(ShowTimeAspect.class);

    /**
     * 处理统计次数等的任务线程池
     */
    @SuppressWarnings("all")
    private final ExecutorService singleThreadPool = Executors
            .newSingleThreadExecutor();

    /**
     * 接收统计计算使用的方法
     */
    private final static Map<String, String> methodList = new ConcurrentHashMap<String, String>();

    /**
     * 统计的实体类 包含某个方法被统计的信息
     */
    private MethodEntity methodEntity = new MethodEntity();

    /**
     * 全局的状态 关闭/开启 所有的方法统计 1 是默认打开  0是关闭
     */
    private static volatile int isOpen = 1;

    /** 
    * @Description: 暴露给外部调用统计使用的方法 
    * @Param:  
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:23 PM 2019/6/27
    */
    public static Map<String, String> getMethodlist() {
        return methodList;
    }

    public static int isOpen() {
        return isOpen;
    }

    /** 
    * @Description: 可以支持动态打开统计开关 
    * @Param:  
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:23 PM 2019/6/27
    */
    public static void setOpen(int isOpen) {
        ShowTimeAspect.isOpen = isOpen;
    }

    /** 
    * @Description: aop切点的区域 
    * @Param:
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:24 PM 2019/6/27
    */
    @Pointcut("@annotation(com.sioo.point.annotation.ShowTime)")
    public void annotationPointCut() {
    }

    /** 
    * @Description: 清除缓存的定时任务 每小时清除一次
    * @Param:  
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:24 PM 2019/6/27
    */
    @Scheduled(cron = "0 0 * * *  ?") //0/5 * *  * * ? 
    public void clearCache() {
        logger.info("====ShowTimeAspect清空缓存列表====");
        methodList.clear();
    }

    /** 
    * @Description: 对切中的方式的具体的计算 
    * @Param:
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:25 PM 2019/6/27
    */
    @Around("annotationPointCut()")
    public Object timeAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ShowTime action = method.getAnnotation(ShowTime.class);
        int threshold = action.threshold();
        // 定义返回对象、得到方法需要的参数  
        Object obj = null;
        Object[] args = joinPoint.getArgs();
        //是否放开统计
        if (isOpen() == 1) {
            long startTime = System.currentTimeMillis();

            obj = dealOldMethod(joinPoint, obj, args);

            long endTime = System.currentTimeMillis();
            //超过阙值才记录.
            if ((endTime - startTime) > threshold) {
                // 获取执行的方法名
                String methodName = extractLogName(method);
                singleThreadPool
                        .submit(new Thread(() -> dealMethodCountList(methodName,
                                startTime, endTime)));
            }
        }
        else {
            //不需要统计时间。直接执行原来方法
            obj = dealOldMethod(joinPoint, obj, args);
        }
        return obj;
    }

    /**
    * @Description: 执行本来的方法
    * @Param:
    * @return:
    * @Author: fanghuaiming
    * @Date: 7:27 PM 2019/6/27
    */
    private Object dealOldMethod(ProceedingJoinPoint joinPoint, Object obj,
                                 Object[] args) throws Throwable {
        if(obj == null){
            return null;
        }else {
            obj = joinPoint.proceed(args);
        }
        return obj;
    }

    /** 
    * @Description: 处理方法的计时 
    * @Param:
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:27 PM 2019/6/27
    */
    void dealMethodCountList(String methodName, long startTime, long endTime) {
        long timeMinus = endTime - startTime;
        if (methodList.get(methodName) == null) {
            methodEntity.setLongestTime(timeMinus);
            methodEntity.setAccessCount(1);
            methodEntity.setAccessTimeSum(timeMinus);
            methodList.put(methodName, JsonUtil.toJsonString(methodEntity));
        }
        else {
            String oldValue = methodList.get(methodName);
            methodEntity = (MethodEntity) JsonUtil.getObjectFromJson(oldValue,
                    MethodEntity.class);
            int count = methodEntity.getAccessCount() + 1;
            long longestTime = methodEntity.getLongestTime();
            if (longestTime < timeMinus) {
                longestTime = timeMinus;
            }
            long sumTime = methodEntity.getAccessTimeSum() + timeMinus;
            methodEntity.setAccessCount(count);
            methodEntity.setAccessTimeSum(sumTime);
            methodEntity.setLongestTime(longestTime);
            methodList.remove(methodName);
            methodList.put(methodName, JsonUtil.toJsonString(methodEntity));
        }

    }

    /**
    * @Description: 抽取方法
    * @Param:
    * @return:
    * @Author: fanghuaiming
    * @Date: 7:28 PM 2019/6/27
    */
    protected String extractLogName(Method method2) {
        String method = method2.getName();
        String clazz = method2.getDeclaringClass().getSimpleName();
        return clazz + "." + method;
    }
}
