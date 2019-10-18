package com.sioo.point.annotation;

import java.lang.annotation.*;

/****
 * @description：幂等性自定义注解
 *
 * @author fanghuaiming
 * @data Created in 2019/6/27 4:37 PM
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Documented
public @interface Idempotent {

    /**
     * @Description: 描述
     * @Param:
     * @return:
     * @Author: fanghuaiming
     * @Date:
     */
    String description() default "";

    /**
    * @Description: 幂等key存在redis的时间
    * @Param:
    * @return:
    * @Author: fanghuaiming
    * @Date: 11:18 AM 2019/10/18
    */
    long expire() default 300;
}
