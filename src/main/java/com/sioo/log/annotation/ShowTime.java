package com.sioo.log.annotation;

import java.lang.annotation.*;

/****
 * @description：展示时间注解
 *
 * @author fanghuaiming
 * @data Created in 2019/6/27 7:18 PM
 *
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShowTime {
    
    /** 
    * @Description: 切换 
    * @Param:  
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:20 PM 2019/6/27
    */
    boolean switchOn();
    
    /** 
    * @Description: 保持 
    * @Param:  
    * @return:  
    * @Author: fanghuaiming
    * @Date: 7:20 PM 2019/6/27
    */
    int threshold();
}
