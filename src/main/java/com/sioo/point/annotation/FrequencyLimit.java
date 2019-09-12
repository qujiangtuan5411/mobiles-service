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
    /**
     *
     * @Description: 限制某时间段内可以访问的次数，默认设置10
     * @return
     *
     * @author qujiangtuan
     * @date 09:57 2019/09/12
     */
    long frequency() default 10;

    /**
     *
     * @Description: 限制访问的某一个时间段，单位为秒，默认值60秒(1分钟)即可
     * @return
     *
     * @author qujiangtuan
     * @date 09:57 2019/09/12
     */
    int timeSecond() default 60;
}
