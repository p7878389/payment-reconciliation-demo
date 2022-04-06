package com.shareworks.reconciliation.services;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * @author martin.peng
 */
@Service
public class RedisServices {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public <T> void setPut(String key, Set<T> set) {
        Assert.isTrue(Objects.nonNull(set), "参数不能为空");
        redisTemplate.opsForSet().add(key, set.stream().toArray());
    }

    public <T> void setPut(String key, T... ts) {
        redisTemplate.opsForSet().add(key, ts);
    }

    /**
     * 获取交集并且将交集存储到unionKey中
     *
     * @param destKey
     * @param otherKey
     */
    public void intersectAndStore(String destKey, Set<String> otherKey) {
        redisTemplate.opsForSet().intersectAndStore(otherKey, destKey);
    }

    /**
     * 差集，获取“key”中存在而“otherKey”中没有的元素
     *
     * @param key
     * @param otherKey
     */
    public <T> Set<T> difference(String key, String otherKey) {
        return (Set<T>) redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 删除set元素
     *
     * @param key
     * @param collection
     * @param <T>
     */
    public <T> void removeSetMember(String key, Collection<T> collection) {
        Assert.notNull(collection, "member collection is not null");
        redisTemplate.opsForSet().remove(key, collection.toArray());
    }
}
