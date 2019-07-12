package com.sioo.annotation;

import java.lang.annotation.*;

/****
 * @description：自定义日志打印注解
 *
 * @author fanghuaiming
 * @data Created in 2019/6/27 4:37 PM
 *
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CustomLog {

    /** 
    * @Description: 描述
    * @Param:
    * @return:
    * @Author: fanghuaiming
    * @Date:
    */
    String description() default "";
    
}
