
package com.sioo.log.bo;

/** 
* @Description: jvm信息 
* @Param:
* @return:  
* @Author: fanghuaiming
* @Date:  
*/
public class JvmInfo {

    /**
     *  是否开启
     */
    private Boolean isOpen;
    /**
     *  阈值
     */
    private String critical;
    /**
     *  当前jvm空闲内存
     */
    private Long freeMemory;
    /**
     *  当前jvm内存总量
     */
    private Long totalMemory;
    /**
     *  jvm试图使用的最大内存
     */
    private Long maxMemory;
    /**
     *  实际可用内存
     */
    private Long actualAvailableMemory;
    /**
     *  当前内存使用百分比
     */
    private String memmoryPercent;
    /**
     *  是否超过阈值 超过：true
     */
    private Boolean judgeValue;

    public Long getActualAvailableMemory() {
        return actualAvailableMemory;
    }

    public void setActualAvailableMemory(Long actualAvailableMemory) {
        this.actualAvailableMemory = actualAvailableMemory;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public Long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(Long freeMemory) {
        this.freeMemory = freeMemory;
    }

    public Long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(Long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public Long getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(Long maxMemory) {
        this.maxMemory = maxMemory;
    }

    public String getMemmoryPercent() {
        return memmoryPercent;
    }

    public void setMemmoryPercent(String memmoryPercent) {
        this.memmoryPercent = memmoryPercent;
    }

    public Boolean getJudgeValue() {
        return judgeValue;
    }

    public void setJudgeValue(Boolean judgeValue) {
        this.judgeValue = judgeValue;
    }

}
