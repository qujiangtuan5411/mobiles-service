package com.sioo.annotation;

import java.lang.annotation.*;

/****
 * @description：错误日志捕获注解
 *
 * @author fanghuaiming
 * @data Created in 2019/6/27 4:48 PM
 *
 */
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ExceptionLog {

    /** 
    * @Description: 描述 
    * @Param:
    * @return:  
    * @Author: fanghuaiming
    * @Date:  
    */
    String description() default "";
}
