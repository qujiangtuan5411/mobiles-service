
package com.sioo.utils;

import com.alibaba.fastjson.JSON;

/** 
* @Description: JSON工具类 
* @Param:  
* @return:  
* @Author: fanghuaiming
* @Date:  
*/
public class JsonUtil {

    /**
     * 将对象转换为Json串
     * @param object
     * @return
     * @Description: 将对象转换为Json串
     * @create date 2017年7月18日下午7:49:24
     */
    public static String toJsonString(Object object) {
        return JSON.toJSONString(object);
    }
    
    /**
     * 将Json串转换为指定对象
     * @param jsonString
     * @param clazz
     * @return
     * @Description: 将Json串转换为指定对象
     * @create date 2017年7月19日下午3:01:33
     */
    public static <T> Object getObjectFromJson(String jsonString, Class<T> clazz) {
        return JSON.parseObject(jsonString, clazz);
    }

    /**
     * 将Json串转换为指定对象
     * @param jsonString
     * @param className 类名称
     * @return
     * @throws ClassNotFoundException
     * @Description: 将Json串转换为指定对象
     * @create date 2017年7月19日上午9:20:20
     */
    public static Object getObjectFromJson(String jsonString, String className) throws ClassNotFoundException {
        return JSON.parseObject(jsonString, Class.forName(className));
    }

}
