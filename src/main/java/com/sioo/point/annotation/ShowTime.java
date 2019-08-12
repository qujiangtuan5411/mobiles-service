package com.sioo.point.annotation;

import java.lang.annotation.*;

/****
 * @description：展示时间注解
 *
 * @author fanghuaiming
 * @data Created in 2019/6/27 7:18 PM
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.METHOD,
        ElementType.TYPE
})
@Documented
public @interface ShowTime {
    
    /** 
    * @Description: 是否记录超时
    * @Param:  
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:20 PM 2019/6/27
    */
    boolean switchOn() default true;
    
    /** 
    * @Description: 请求调用时间毫秒值
    * @Param:  
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:20 PM 2019/6/27
    */
    long threshold() default 5000;
}
