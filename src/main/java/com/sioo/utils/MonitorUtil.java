
package com.sioo.utils;

import com.sioo.bo.JvmInfo;
import com.sioo.bo.ThreadPoolInfo;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.concurrent.ThreadPoolExecutor;

/** 
* @Description: 性能监控Util
* @Param:  
* @return:
* @Author: fanghuaiming
* @Date:  
*/
public class MonitorUtil {
    
    /** 
    * @Description: 初始化jvm信息 
    * @Param:
    * @return:  
    * @Author: fanghuaiming
    * @Date: 6:55 PM 2019/6/27
    */
    public static JvmInfo initJvmInfo(Boolean isOpen, String critical) {
        JvmInfo jvmInfo = new JvmInfo();
        jvmInfo.setIsOpen(isOpen);
        jvmInfo.setCritical(critical);
        Runtime rt = Runtime.getRuntime();
        //相当于当前JVM已使用的内存及freeMemory()的总和
        long totalMemorySize = rt.totalMemory();
        jvmInfo.setTotalMemory(totalMemorySize);
        //最大分配内存
        long maxMemorySize = rt.maxMemory();
        jvmInfo.setMaxMemory(maxMemorySize);
        //JVM空闲内存
        long freeMemorySize = rt.freeMemory();
        jvmInfo.setFreeMemory(freeMemorySize);
        // JVM实际可用内存:并不等于freeMemory(),而应该等于maxMemory()-totalMemory()+freeMemory()
        long actualAvailableMemory = maxMemorySize - totalMemorySize + freeMemorySize;
        jvmInfo.setActualAvailableMemory(actualAvailableMemory);
        String availableMemoryPercent = MonitorUtil.computePercent(actualAvailableMemory, maxMemorySize);
        jvmInfo.setMemmoryPercent(availableMemoryPercent);
        int judgeValue = MonitorUtil.comparePercent(availableMemoryPercent, critical);
        jvmInfo.setJudgeValue(judgeValue == -1);
        return jvmInfo;
    }
    
    /** 
    * @Description: 获得线程池信息 
    * @Param:  
    * @return:  
    * @Author: fanghuaiming
    * @Date:  
    */
    public static ThreadPoolInfo initThreadPoolInfo(Boolean isOpen, ThreadPoolExecutor threadPoolExecutor) {
        ThreadPoolInfo info = new ThreadPoolInfo();
        info.setIsOpen(isOpen);
        info.setPoolSize(threadPoolExecutor.getPoolSize());
        info.setCorePoolSize(threadPoolExecutor.getCorePoolSize());
        info.setMaximumPoolSize(threadPoolExecutor.getMaximumPoolSize());
        info.setActiveCount(threadPoolExecutor.getActiveCount());
        info.setTaskCount(threadPoolExecutor.getTaskCount());
        info.setCompletedTaskCount(threadPoolExecutor.getCompletedTaskCount());
        return info;
    }

    /** 
    * @Description: 获取百分比 
    * @Param:  
    * @return:  
    * @Author: fanghuaiming
    * @Date:  
    */
    public static String computePercent(double molecular, double denominator) {
        String str;
        double p = molecular / denominator;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);
        str = nf.format(p);
        return str;
    }

    /** 
    * @Description: 判断某种情况百分比低于某一界定值的方法 
    * @Param:  
    * @return:  
    * @Author: fanghuaiming
    * @Date:  
    */
    public static int comparePercent(String usedPercent, String critical) {
        String tempA = usedPercent.substring(0, usedPercent.indexOf("%"));
        String tempB = critical.substring(0, critical.indexOf("%"));
        BigDecimal dataA = new BigDecimal(tempA);
        BigDecimal dataB = new BigDecimal(tempB);
        return dataA.compareTo(dataB);
    }

}
