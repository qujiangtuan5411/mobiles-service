package com.sioo.point.annotation;

import java.lang.annotation.*;

/****
 * @description：JVM监控
 *
 * @author fanghuaiming
 * @data Created in 2019/6/27 4:50 PM
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Documented
public @interface JvmMonitor {
    /**
    * @Description: 是否开启监控
    * @Param:
    * @return:
    * @Author: fanghuaiming
    * @Date:
    */
    String isOpen() default "true";

    /**
    * @Description: 阀值:可用内存数占比最小比值
    * @Param:
    * @return:
    * @Author: fanghuaiming
    * @Date:
    */
    String critical() default "10%";
}
