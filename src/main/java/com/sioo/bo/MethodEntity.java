package com.sioo.bo;

/****
 * @description：存储map 里面用到的统计对象
 *
 * @author fanghuaiming
 * @data Created in 2019/6/27 7:17 PM
 *
 */
public class MethodEntity {

    private Integer accessCount;
    private Long accessTimeSum;
    private Long longestTime;

    public Integer getAccessCount() {
        return accessCount;
    }
    public void setAccessCount(Integer accessCount) {
        this.accessCount = accessCount;
    }
    public Long getAccessTimeSum() {
        return accessTimeSum;
    }
    public void setAccessTimeSum(Long accessTimeSum) {
        this.accessTimeSum = accessTimeSum;
    }
    public Long getLongestTime() {
        return longestTime;
    }
    public void setLongestTime(Long longestTime) {
        this.longestTime = longestTime;
    }
    @Override
    public String toString() {
        String t = "访问次数:"+ accessCount +" 最大访问时间:"+longestTime +" 平均访问时长:" + accessTimeSum/accessCount;
        return t;
    }
    public void clear(){
        accessCount = null;
        accessTimeSum = null;
        longestTime = null;
    }

}
