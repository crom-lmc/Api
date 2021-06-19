package space.laimengchao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 */
@Component
public class RedisUtils {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 新增缓存
     * @param key
     * @param value
     */
    public void set(String key,String value){
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 新增缓存并设置过期时间
     * @param key
     * @param value
     * @param expire
     */
    public void set(String key,String value,long expire){
        set(key, value);
        stringRedisTemplate.expire(key,expire,TimeUnit.SECONDS);
    }

    /**
     * 获取缓存值
     * @param key
     * @return
     */
    public String getValue(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 删除key
     * @param key
     */
    public void deleteKey(String key){
        stringRedisTemplate.delete(key);
    }

    /**
     * 判断是否包含key
     * @param key
     */
    public void hasKey(String key){
        stringRedisTemplate.hasKey(key);
    }

    /**
     * 获取过期时间
     * @param key
     * @return
     */
    public long getExpire(String key){
        return stringRedisTemplate.getExpire(key);
    }

}
