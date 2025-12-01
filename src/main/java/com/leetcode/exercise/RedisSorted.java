package com.leetcode.exercise;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;
import redis.clients.jedis.resps.Tuple;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RedisSorted {
    private final JedisPool jedisPool;
    public static final String key = "visitSorted";

    public RedisSorted() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);

        jedisPool = new JedisPool(jedisPoolConfig, "localhost", 6379, 3000);
    }

    public void remove() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.zremrangeByRank(key, 0, -1);
        }
    }

    public double addVisit(String userId) {
        try (Jedis jedis = jedisPool.getResource()) {
            double zincrby = jedis.zincrby(key, 1, userId);
            System.out.println("userId:" + userId + ", zincrby:" + zincrby);
            return zincrby;
        }
    }

    public Map<String, Long> sortedVisit() {
        try (Jedis jedis = jedisPool.getResource()) {
            List<Tuple> tuples = jedis.zrevrangeWithScores(key, 0, -1);
            Map<String, Long> result = new HashMap<>();
            for (Tuple tuple : tuples) {
                String tmpUserId = tuple.getElement();
                long sequence = Math.round(tuple.getScore());
                result.put(tmpUserId, sequence);
            }
            return
                    result.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }
    }

    /**
     * 实现基本的set、get操作
     * @param key
     * @param value
     */
    public void addAndGet(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            String set = jedis.set(key, value);
            System.out.println("set:" + set);

            String valueSaved = jedis.get(key);
            System.out.println("get:" + valueSaved);
        }
    }

    /**
     * 维护一个计数器
     * @param key
     */
    public void incrCount(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String oldCount = jedis.get(key);
            long newCount = jedis.incr(key);
            System.out.println("incrCount:" + oldCount + ", newCount:" + newCount);
        }
    }

    /**
     * 获取分布式锁（只有当 key 不存在时才能设置成功）
     * @param key 锁的 key
     * @return true 如果成功获取锁，false 如果锁已被占用
     */
    public boolean getDistributeKey(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            SetParams setParams = new SetParams();
            setParams.nx();  // NX: 只有当 key 不存在时才设置（Not eXists）
            setParams.ex(100L);  // EX: 设置过期时间为 100 秒
            // 如果 key 不存在，set 返回 "OK"，如果 key 已存在，返回 null
            String set = jedis.set(key, "1", setParams);
            return "OK".equals(set);
        }
    }

    public boolean releaseDistributeKey(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, 1, key, "1");
            return "1".equals(result.toString());
        }
    }

    private static final String LRU_HASH_KEY = "lru:cache:data";
    private static final String LRU_ZSET_KEY = "lru:cache:time";
    private static final Integer LRU_SIZE = 5;

    public void lruSet(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset(LRU_HASH_KEY, key, value);
            jedis.zadd(LRU_ZSET_KEY, System.currentTimeMillis(), key);
            long zcard = jedis.zcard(LRU_ZSET_KEY);
            if(zcard <= LRU_SIZE) {
                return;
            }

            List<String> zrange = jedis.zrange(key, 0, 0);
            jedis.hdel(LRU_HASH_KEY, zrange.getFirst());
            jedis.zrem(key, zrange.getFirst());
        }
    }

    public String lruGet(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String hget = jedis.hget(LRU_HASH_KEY, key);
            if(null != hget) {
                jedis.zadd(LRU_ZSET_KEY, System.currentTimeMillis(), key);
            }
            return hget;
        }
    }

    public void zget(String key, String member) {
        try (Jedis jedis = jedisPool.getResource()) {
            Double zscore = jedis.zscore(key, member);
            System.out.println("zscore:" + zscore);
            jedis.zincrby(key, 1, member);
            jedis.sadd(key, member);
        }
    }
}
