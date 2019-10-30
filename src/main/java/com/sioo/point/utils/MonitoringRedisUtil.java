package com.sioo.point.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
* @Description: redisTemplate封装
* @Param:
* @return:
* @Author: fanghuaiming
* @Date: 1:36 PM 2019/8/2
*/
@SuppressWarnings("all")
@Component
public class MonitoringRedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /*@Autowired
    private RedisTemplate slaveRedisTemplate;*/

    /**
    * @Description: 指定缓存失效时间
    * @param key 键
    * @param time 时间(秒)
    * @return:
    * @Author: fanghuaiming
    * @Date: 1:37 PM 2019/8/2
    */
    public boolean expire(String key,long time){
        try {
            if(time>0){
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
    * @Description: 根据key 获取过期时间
    * @Param: key 键 不能为null
    * @return:  时间(秒) 返回0代表为永久有效
    * @Author: fanghuaiming
    * @Date: 1:37 PM 2019/8/2
    */
    public long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
    * @Description: 判断key是否存在
    * @Param:  key 键
    * @return:  true 存在 false不存在
    * @Author: fanghuaiming
    * @Date: 1:38 PM 2019/8/2
    */
    public boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
    * @Description:  删除缓存
    * @Param:  key 可以传一个值 或多个
    * @Author: fanghuaiming
    * @Date: 1:38 PM 2019/8/2
    */
    @SuppressWarnings("unchecked")
    public void del(String ... key){
        if(key!=null&&key.length>0){
            if(key.length==1){
                redisTemplate.delete(key[0]);
            }else{
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    //============================String=============================
    /**
    * @Description: 普通缓存获取
    * @Param:  key 键
    * @return:  值
    * @Author: fanghuaiming
    * @Date: 1:39 PM 2019/8/2
    */
    public Object get(String key){
        return key==null?null: redisTemplate.opsForValue().get(key);
    }

    /**
    * @Description: 普通缓存放入
    * @Param:  key 键 value 值
    * @return:  true成功 false失败
    * @Author: fanghuaiming
    * @Date: 1:39 PM 2019/8/2
    */
    public boolean set(String key,Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
    * @Description:  普通缓存放入并设置时间
    * @param key 键
    * @param value 值
    * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
    * @return:  true成功 false 失败
    * @Author: fanghuaiming
    * @Date: 1:39 PM 2019/8/2
    */
    public boolean set(String key,Object value,long time){
        try {
            if(time>0){
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else{
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description: 递增
     * @param key 键
     * @param delta 要增加几(大于0)
     * @Author: fanghuaiming
     * @Date: 1:40 PM 2019/8/2
     */
    public long incr(String key, long delta){
        if(delta<0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * @Description: 递减
     * @param key 键
     * @param delta 要减少几(小于0)
     * @return
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public long decr(String key, long delta){
        if(delta<0){
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    //================================Map=================================
    /**
     * @Description:HashGet
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return 值
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public Object hget(String key,String item){
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * @Description:获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public Map<Object,Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * @Description:HashSet
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public boolean hmset(String key, Map<String,Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description: 并设置时间
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public boolean hmset(String key, Map<String,Object> map, long time){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description:向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 false失败
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public boolean hset(String key,String item,Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description:向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public boolean hset(String key,String item,Object value,long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description:删除hash表中的值
     * @param key 键 不能为null
     * @param item 项 可以使多个 不能为null
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public void hdel(String key, Object... item){
        redisTemplate.opsForHash().delete(key,item);
    }

    /**
     * @Description:判断hash表中是否有该项的值
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public boolean hHasKey(String key, String item){
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * @Description:hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 键
     * @param item 项
     * @param by 要增加几(大于0)
     * @return
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public double hincr(String key, String item,double by){
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * @Description:hash递减
     * @param key 键
     * @param item 项
     * @param by 要减少记(小于0)
     * @return
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public double hdecr(String key, String item,double by){
        return redisTemplate.opsForHash().increment(key, item,-by);
    }

    //============================set=============================
    /**
     * @Description:根据key获取Set中的所有值
     * @param key 键
     * @return
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public Set<Object> sGet(String key){
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description:根据value从一个set中查询,是否存在
     * @param key 键
     * @param value 值
     * @return true 存在 false不存在
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public boolean sHasKey(String key,Object value){
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description:将数据放入set缓存
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public long sSet(String key, Object...values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @Description:将set数据放入缓存
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public long sSetAndTime(String key,long time,Object...values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if(time>0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @Description:获取set缓存的长度
     * @param key 键
     * @return
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public long sGetSetSize(String key){
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @Description:移除值为value的
     * @param key 键
     * @param values 值 可以是多个
     * @return 移除的个数
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public long setRemove(String key, Object ...values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    //===============================list=================================

    /**
     * @Description:获取list缓存的内容
     * @param key 键
     * @param start 开始
     * @param end 结束  0 到 -1代表所有值
     * @return
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public List<Object> lGet(String key, long start, long end){
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description:获取list缓存的长度
     * @param key 键
     * @return
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public long lGetListSize(String key){
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @Description:通过索引 获取list中的值
     * @param key 键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public Object lGetIndex(String key,long index){
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description:将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description:将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description:将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description:将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description:根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public boolean lUpdateIndex(String key, long index,Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description:移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public long lRemove(String key,long count,Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @Description:模糊查询获取key值
     * @param pattern
     * @return
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public Set keys(String pattern){
        return redisTemplate.keys(pattern);
    }

    /**
     * @Description:使用Redis的消息队列
     * @param channel
     * @param message 消息内容
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public void convertAndSend(String channel, Object message){
        redisTemplate.convertAndSend(channel,message);
    }


    //=========BoundListOperations 用法 start============

    /**
     * @Description:将数据添加到Redis的list中（从右边添加）
     * @param listKey
     * @param expireEnum 有效期的枚举类
     * @param values 待添加的数据
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    /*public void addToListRight(String listKey, Status.ExpireEnum expireEnum, Object... values) {
        //绑定操作
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        //插入数据
        boundValueOperations.rightPushAll(values);
        //设置过期时间
        boundValueOperations.expire(expireEnum.getTime(),expireEnum.getTimeUnit());
    }*/
    /**
     * @Description:根据起始结束序号遍历Redis中的list
     * @param listKey
     * @param start  起始序号
     * @param end  结束序号
     * @return
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public List<Object> rangeList(String listKey, long start, long end) {
        //绑定操作
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        //查询数据
        return boundValueOperations.range(start, end);
    }
    /**
     * @Description:弹出右边的值 --- 并且移除这个值
     * @param listKey
     * @Author: fanghuaiming
     * @Date: 1:41 PM 2019/8/2
     */
    public Object rifhtPop(String listKey){
        //绑定操作
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        return boundValueOperations.rightPop();
    }

    /**
     * 扣费
     *
     * @param uid   用户
     * @param count 扣费条数
     * @return 大于0则正常扣费, 小于0则计费失败.
     */
    public long chargedBy(int uid, int freeNum) {
        long free = -1;
        try {
            free = this.decr("sms_free_" + uid, freeNum);
            if (free < 0) {
                this.incr("sms_free_" + uid, freeNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return free;
    }

    /**
     * 充值
     *
     * @param uid   用户
     * @param count 充值条数
     * @return 充值后条数.
     */
    public int accountBy(int uid, int freeNum) {
        int balance = -1;
        try {
            this.incr("sms_free_" + uid, freeNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return balance;
    }
    //=========BoundListOperations 用法 End============

    /**
     * @Description: 对hash类型的数据操作
     * @Param:
     * @return:
     * @Author: fanghuaiming
     * @Date: 1:24 PM 2019/8/2
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations() {
//        slaveRedisTemplate.opsForValue().set("slave","slave",10,TimeUnit.SECONDS);
        return redisTemplate.opsForHash();
    }

    /**
     * @Description: 对redis字符串类型数据操作
     * @Param:
     * @return:
     * @Author: fanghuaiming
     * @Date: 1:24 PM 2019/8/2
     */
    @Bean
    public ValueOperations<String, Object> valueOperations() {
        return redisTemplate.opsForValue();
    }

    /**
     * @Description: 对链表类型的数据操作
     * @Param:
     * @return:
     * @Author: fanghuaiming
     * @Date: 1:25 PM 2019/8/2
     */
    @Bean
    public ListOperations<String, Object> listOperations() {
        return redisTemplate.opsForList();
    }

    /**
     * @Description: 对无序集合类型的数据操作
     * @Param:
     * @return:
     * @Author: fanghuaiming
     * @Date: 1:25 PM 2019/8/2
     */
    @Bean
    public SetOperations<String, Object> setOperations() {
        return redisTemplate.opsForSet();
    }

    /**
     * @Description: 对有序集合类型的数据操作
     * @Param:
     * @return:
     * @Author: fanghuaiming
     * @Date: 1:25 PM 2019/8/2
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations() {
        return redisTemplate.opsForZSet();
    }

}