package com.sioo.log.annotation;

import java.lang.annotation.*;

/****
 * @description：请求参数等Log注解
 *
 * @author fanghuaiming
 * @data Created in 2019/6/27 4:52 PM
 *
 */
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParameterLog {

    /** 
    * @Description: 描述 
    * @Param:
    * @return:  
    * @Author: fanghuaiming
    * @Date:  
    */
    String description() default "";
}
