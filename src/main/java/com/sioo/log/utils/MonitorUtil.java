
package com.sioo.log.utils;

import com.sioo.log.bo.JvmInfo;
import com.sioo.log.bo.ThreadPoolInfo;

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
     * 初始化jvm信息
     * @param isOpen
     * @param critical
     * @return
     * @Description: 
     * @create date 2017年8月14日下午3:33:40
     */
    public static JvmInfo initJvmInfo(Boolean isOpen, String critical) {
        JvmInfo jvmInfo = new JvmInfo();
        jvmInfo.setIsOpen(isOpen);
        jvmInfo.setCritical(critical);
        Runtime rt = Runtime.getRuntime();
        long totalMemorySize = rt.totalMemory(); //相当于当前JVM已使用的内存及freeMemory()的总和
        jvmInfo.setTotalMemory(totalMemorySize);
        long maxMemorySize = rt.maxMemory(); //最大分配内存
        jvmInfo.setMaxMemory(maxMemorySize);
        long freeMemorySize = rt.freeMemory(); //JVM空闲内存
        jvmInfo.setFreeMemory(freeMemorySize);
        long actualAvailableMemory = maxMemorySize - totalMemorySize + freeMemorySize; // JVM实际可用内存:并不等于freeMemory(),而应该等于maxMemory()-totalMemory()+freeMemory()
        jvmInfo.setActualAvailableMemory(actualAvailableMemory);
        String availableMemoryPercent = MonitorUtil.computePercent(actualAvailableMemory, maxMemorySize);
        jvmInfo.setMemmoryPercent(availableMemoryPercent);
        int judgeValue = MonitorUtil.comparePercent(availableMemoryPercent, critical);
        jvmInfo.setJudgeValue(judgeValue == -1);
        return jvmInfo;
    }
    
    /**
     * 获得线程池信息
     * @param isOpen
     * @param threadPoolExecutor
     * @return
     * @Description: 
     * @create date 2017年8月14日下午5:36:37
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
     * 获取百分比
     * @param molecular 分子
     * @param denominator 分母
     * @return
     * @Description: 保留两位小数
     * @create date 2017年8月11日上午10:52:57
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
     * 判断某种情况百分比低于某一界定值的方法
     * @param usedPercent
     * @param critical
     * @return
     * @Description: 
     * @create date 2017年8月11日上午11:06:40
     */
    public static int comparePercent(String usedPercent, String critical) {
        String tempA = usedPercent.substring(0, usedPercent.indexOf("%"));
        String tempB = critical.substring(0, critical.indexOf("%"));
        BigDecimal dataA = new BigDecimal(tempA);
        BigDecimal dataB = new BigDecimal(tempB);
        return dataA.compareTo(dataB);
    }

}
