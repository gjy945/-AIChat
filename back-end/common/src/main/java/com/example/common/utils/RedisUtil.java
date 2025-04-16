package com.example.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 */
@Slf4j
public class RedisUtil {

    private static RedisTemplate<String, Object> redisTemplate = SpringContextUtils.getBean("redisTemplate", RedisTemplate.class);
    public static final StringRedisTemplate STRING_REDIS_TEMPLATE = SpringContextUtils.getBean("stringRedisTemplate", StringRedisTemplate.class);

    // =============== Common ==========================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return boolean
     */
    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("设置 Redis key {} 失效时间 {} 秒失败", key, time, e);
            return false;
        }
    }

    /**
     * 根据 key 获取过期时间
     *
     * @param key 键 不能为 null
     * @return 时间(秒) 返回 0 代表永久有效，负数代表未设置失效时间
     */
    public static Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断 key 是否存在
     *
     * @param key 键
     * @return true 存在 false 不存在
     */
    public static Boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("判断 Redis key {} 是否存在失败", key, e);
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    // =============== String ==========================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return key == null ? null : (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true 成功 false 失败
     */
    public static boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("设置 Redis key {} 值为 {} 失败", key, value, e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间(默认时间单位:秒)
     *
     * @param key        键
     * @param value      值
     * @param expireTime 时间(秒) time 要大于 0，小于等于 0 将设置为无限期
     * @return true 成功 false 失败
     */
    public static boolean set(String key, Object value, long expireTime) {
        return set(key, value, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间
     * @param timeUnit   时间单位
     * @return true 成功 false 失败
     */
    public static boolean set(String key, Object value, long expireTime, TimeUnit timeUnit) {
        try {
            if (expireTime > 0) {
                redisTemplate.opsForValue().set(key, value, expireTime, timeUnit);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("设置 Redis key {} 值为 {} 并设置过期时间 {} {} 失败", key, value, expireTime, timeUnit, e);
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于 0)
     * @return 递增后的值
     */
    public static Long incr(String key, long delta) {
        if (delta < 0) {
            log.error("Redis key {} 递增因子必须大于 0，当前值为 {}", key, delta);
            throw new RuntimeException("递增因子必须大于 0");
        }
        return STRING_REDIS_TEMPLATE.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(大于 0)
     * @return 递减后的值
     */
    public static Long decr(String key, long delta) {
        if (delta < 0) {
            log.error("Redis key {} 递减因子必须大于 0，当前值为 {}", key, delta);
            throw new RuntimeException("递减因子必须大于 0");
        }
        return STRING_REDIS_TEMPLATE.opsForValue().increment(key, -delta);
    }

    // =============== Hash ==========================

    /**
     * HashGet
     *
     * @param key     键 不能为 null
     * @param hashKey 项 不能为 null
     * @return 值
     */
    public static Object hget(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获取 hashKey 对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key     键
     * @param hashKey 项
     * @param value   值
     * @return true 成功 false 失败
     */
    public static boolean hset(String key, String hashKey, Object value) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
            return true;
        } catch (Exception e) {
            log.error("设置 Redis key {} 的 hashKey {} 值为 {} 失败", key, hashKey, value, e);
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key     键
     * @param hashKey 项
     * @param value   值
     * @param time    时间(秒) 注意:如果已存在的 hashKey 也会覆盖,并重新设置时间
     * @return true 成功 false 失败
     */
    public static boolean hset(String key, String hashKey, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("设置 Redis key {} 的 hashKey {} 值为 {} 并设置过期时间 {} 秒失败", key, hashKey, value, time, e);
            return false;
        }
    }

    /**
     * 删除 hash 表中的值
     *
     * @param key     键 不能为 null
     * @param hashKey 项 可以是多个，不能为 null
     */
    public static void hdel(String key, Object... hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * 判断 hash 表中是否存在该项的值
     *
     * @param key     键 不能为 null
     * @param hashKey 项 不能为 null
     * @return true 存在 false 不存在
     */
    public static boolean hHasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * hash 递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key     键
     * @param hashKey 项
     * @param delta   要增加几(大于 0)
     * @return
     */
    public static Long hincr(String key, String hashKey, long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * hash 递减
     *
     * @param key     键
     * @param hashKey 项
     * @param delta   要减少几(大于 0)
     * @return
     */
    public static Long hdecr(String key, String hashKey, long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    /**
     * 获取 hash 表的大小
     *
     * @param key 键
     * @return Long
     */
    public static Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    // =============== List ==========================

    /**
     * 获取 list 缓存的内容
     *
     * @param key   键
     * @param start 开始 0 是第一个元素
     * @param end   结束 -1 代表所有值
     * @return List<Object>
     */
    public static List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("获取 Redis key {} 的列表从 {} 到 {} 失败", key, start, end, e);
            return null;
        }
    }

    /**
     * 获取 list 缓存的长度
     *
     * @param key 键
     * @return Long
     */
    public static Long lSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("获取 Redis key {} 的列表长度失败", key, e);
            return 0L;
        }
    }

    /**
     * 通过索引获取 list 中的值
     *
     * @param key   键
     * @param index 索引 index>=0 时， 0 表头，1 第二个元素，依次类推；index<0 时，-1，表尾，-2 倒数第二个元素，依次类推
     * @return 值
     */
    public static Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("获取 Redis key {} 列表中索引为 {} 的值失败", key, index, e);
            return null;
        }
    }

    /**
     * 将 list 放入缓存
     *
     * @param key   键
     * @param value 值
     * @return true 成功 false 失败
     */
    public static boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("设置 Redis key {} 的列表值为 {} 失败", key, value, e);
            return false;
        }
    }

    /**
     * 将 list 放入缓存并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return true 成功 false 失败
     */
    public static boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("设置 Redis key {} 的列表值为 {} 并设置过期时间 {} 秒失败", key, value, time, e);
            return false;
        }
    }

    /**
     * 将 list 放入缓存
     *
     * @param key   键
     * @param value List<Object>
     * @return true 成功 false 失败
     */
    public static boolean lSetList(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("设置 Redis key {} 的列表值为 {} 失败", key, value, e);
            return false;
        }
    }

    /**
     * 将 list 放入缓存并设置时间
     *
     * @param key   键
     * @param value List<Object>
     * @param time  时间(秒)
     * @return true 成功 false 失败
     */
    public static boolean lSetList(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("设置 Redis key {} 的列表值为 {} 并设置过期时间 {} 秒失败", key, value, time, e);
            return false;
        }
    }

    /**
     * 根据索引修改 list 中的某条数据
     *
     * @param key   键
     * @param index 索引 index>=0 时， 0 表头，1 第二个元素，依次类推；index<0 时，-1，表尾，-2 倒数第二个元素，依次类推
     * @param value 值
     * @return true 成功 false 失败
     */
    public static boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("更新 Redis key {} 列表中索引为 {} 的值为 {} 失败", key, index, value, e);
            return false;
        }
    }

    /**
     * 移除 N 个值为 value 的元素
     *
     * @param key   键
     * @param count 移除多少个 count>0 从头到尾移除 count 个值为 value 的元素；count<0 从尾到头移除 count 的绝对值个值为 value 的元素；count=0 移除所有值为 value 的元素
     * @param value 值
     * @return 移除的个数
     */
    public static Long lRemove(String key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            log.error("移除 Redis key {} 列表中 {} 个值为 {} 的元素失败", key, count, value, e);
            return 0L;
        }
    }

    /**
     * 从右边拿出来一个
     *
     * @param key     string key
     * @param timeout Long 超时秒数
     */
    public static Object getRightPop(String key, Long timeout) {
        return redisTemplate.opsForList().rightPop(key, timeout, TimeUnit.SECONDS);
    }

    // =============== Set ==========================

    /**
     * 获取 Set 中的所有值
     *
     * @param key 键
     * @return Set<Object>
     */
    public static Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("获取 Redis key {} 的 Set 失败", key, e);
            return null;
        }
    }

    /**
     * 将数据放入 Set 缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功放入的个数
     */
    public static Long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("向 Redis key {} 的 Set 中添加值 {} 失败", key, Arrays.toString(values), e);
            return 0L;
        }
    }

    /**
     * 移除 Set 缓存中的值
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除成功的个数
     */
    public static Long sRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            log.error("从 Redis key {} 的 Set 中移除值 {} 失败", key, Arrays.toString(values), e);
            return 0L;
        }
    }

    /**
     * 判断 Set 缓存中是否包含某个值
     *
     * @param key   键
     * @param value 值
     * @return true 包含 false 不包含
     */
    public static Boolean sIsMember(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("判断 Redis key {} 的 Set 是否包含值 {} 失败", key, value, e);
            return false;
        }
    }

    /**
     * 获取 Set 缓存的长度
     *
     * @param key 键
     * @return Long
     */
    public static Long sSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("获取 Redis key {} 的 Set 长度失败", key, e);
            return 0L;
        }
    }

    // =============== ZSet (Sorted Set) ==========================

    /**
     * 向 Sorted Set 中添加值
     *
     * @param key   键
     * @param value 值
     * @param score 分数
     */
    public static Boolean zAdd(String key, Object value, double score) {
        try {
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            log.error("向 Redis key {} 的 Sorted Set 中添加值 {} (score: {}) 失败", key, value, score, e);
            return false;
        }
    }

    /**
     * 从 Sorted Set 中移除指定的值
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除成功的个数
     */
    public static Long zRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForZSet().remove(key, values);
        } catch (Exception e) {
            log.error("从 Redis key {} 的 Sorted Set 中移除值 {} 失败", key, Arrays.toString(values), e);
            return 0L;
        }
    }

    /**
     * 获取 Sorted Set 中指定范围的值（按分数升序）
     *
     * @param key   键
     * @param start 开始分数
     * @param end   结束分数
     * @return Set<Object>
     */
    public static Set<Object> zRangeByScore(String key, double start, double end) {
        try {
            return redisTemplate.opsForZSet().rangeByScore(key, start, end);
        } catch (Exception e) {
            log.error("获取 Redis key {} 的 Sorted Set 中分数从 {} 到 {} 的值失败", key, start, end, e);
            return null;
        }
    }

    /**
     * 获取 Sorted Set 中指定范围的值（按索引升序）
     *
     * @param key   键
     * @param start 开始索引
     * @param end   结束索引
     * @return Set<Object>
     */
    public static Set<Object> zRange(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            log.error("获取 Redis key {} 的 Sorted Set 中索引从 {} 到 {} 的值失败", key, start, end, e);
            return null;
        }
    }

    /**
     * 获取 Sorted Set 中指定值的分数
     *
     * @param key   键
     * @param value 值
     * @return 分数，如果值不存在则返回 null
     */
    public static Double zScore(String key, Object value) {
        try {
            return redisTemplate.opsForZSet().score(key, value);
        } catch (Exception e) {
            log.error("获取 Redis key {} 的 Sorted Set 中值 {} 的分数失败", key, value, e);
            return null;
        }
    }

    /**
     * 获取 Sorted Set 的大小
     *
     * @param key 键
     * @return Long
     */
    public static Long zSize(String key) {
        try {
            return redisTemplate.opsForZSet().size(key);
        } catch (Exception e) {
            log.error("获取 Redis key {} 的 Sorted Set 大小失败", key, e);
            return 0L;
        }
    }

    /**
     * Sorted Set 增加元素的 score 值
     *
     * @param key   键
     * @param value 值
     * @param delta 要增加的分数（可以为负数）
     * @return 增加后的 score 值
     */
    public static Double zIncrScore(String key, Object value, double delta) {
        try {
            return redisTemplate.opsForZSet().incrementScore(key, value, delta);
        } catch (Exception e) {
            log.error("Redis key {} 的 Sorted Set 中值 {} 的 score 增加 {} 失败", key, value, delta, e);
            return null;
        }
    }
}