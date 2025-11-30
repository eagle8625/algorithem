package com.leetcode.exercise;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
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

    public void remove(){
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
}
