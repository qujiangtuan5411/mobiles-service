package com.sioo.point.annotation;

import java.lang.annotation.*;

/****
 * @description：根据调用次数拦截
 *
 * @author liuquan
 * @data Created in 2019/09/09 15:18
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.METHOD,
        ElementType.TYPE
})
@Documented
public @interface FrequencyLimit {
    
    long frequency() default 10;
}
