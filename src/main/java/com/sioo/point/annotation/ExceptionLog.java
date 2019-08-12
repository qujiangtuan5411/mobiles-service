package com.sioo.point.annotation;

import java.lang.annotation.*;

/****
 * @description：错误日志捕获注解
 *
 * @author fanghuaiming
 * @data Created in 2019/6/27 4:48 PM
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Documented
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
