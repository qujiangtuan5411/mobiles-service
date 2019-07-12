package com.sioo.bo;

/**
* @Description: 线程池信息
* @Param:
* @return:
* @Author: fanghuaiming
* @Date:
*/
public class ThreadPoolInfo {

    /**
     *  是否已开启
     */
    private Boolean isOpen;
    /**
     *  当前线程池的线程数量
     */
    private Integer poolSize;
    /**
     *  线程池维护线程的最少数量
     */
    private Integer corePoolSize;
    /**
     *  线程池维护线程的最大数量
     */
    private Integer maximumPoolSize;
    /**
     *  活动的线程数
     */
    private Integer activeCount;
    /**
     *  线程池需要执行的任务数
     */
    private Long taskCount;
    /**
     *  线程池在运行过程中已完成的任务数量
     */
    private Long completedTaskCount;

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Integer getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(Integer activeCount) {
        this.activeCount = activeCount;
    }

    public Long getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Long taskCount) {
        this.taskCount = taskCount;
    }

    public Long getCompletedTaskCount() {
        return completedTaskCount;
    }

    public void setCompletedTaskCount(Long completedTaskCount) {
        this.completedTaskCount = completedTaskCount;
    }

}
