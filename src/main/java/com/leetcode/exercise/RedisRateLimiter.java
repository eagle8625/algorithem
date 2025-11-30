package com.leetcode.exercise;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 基于 Redis 的限流器实现
 * 使用滑动窗口算法（Sliding Window）
 * 
 * 功能：
 * - 限制指定时间窗口内的请求数量
 * - 支持分布式环境下的限流
 * - 使用 Redis 的 Lua 脚本保证原子性操作
 * 
 * @author Redis Expert
 */
public class RedisRateLimiter {
    
    private final JedisPool jedisPool;
    private final String keyPrefix;
    
    /**
     * 限流器配置
     */
    public static class RateLimitConfig {
        /** 时间窗口大小（秒） */
        private final int windowSize;
        /** 允许的最大请求数 */
        private final int maxRequests;
        
        public RateLimitConfig(int windowSize, int maxRequests) {
            this.windowSize = windowSize;
            this.maxRequests = maxRequests;
        }
        
        public int getWindowSize() {
            return windowSize;
        }
        
        public int getMaxRequests() {
            return maxRequests;
        }
    }
    
    /**
     * 构造函数 - 使用默认 Redis 配置（localhost:6379）
     */
    public RedisRateLimiter() {
        this("localhost", 6379, "ratelimiter");
    }
    
    /**
     * 构造函数 - 指定 Redis 连接信息
     * 
     * @param host Redis 主机地址
     * @param port Redis 端口
     * @param keyPrefix Redis key 前缀
     */
    public RedisRateLimiter(String host, int port, String keyPrefix) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(20);
        poolConfig.setMaxIdle(10);
        poolConfig.setMinIdle(5);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        
        this.jedisPool = new JedisPool(poolConfig, host, port);
        this.keyPrefix = keyPrefix;
    }
    
    /**
     * 检查是否允许请求
     * 
     * @param identifier 请求标识（如用户ID、IP地址等）
     * @param config 限流配置
     * @return true 如果允许请求，false 如果超过限制
     */
    public boolean isAllowed(String identifier, RateLimitConfig config) {
        String key = keyPrefix + ":" + identifier;
        // 使用毫秒级时间戳 + 纳秒级时间戳，确保同一毫秒内的多次请求也有唯一标识
        long currentTimeMs = System.currentTimeMillis();
        // 使用 System.nanoTime() 的后6位作为唯一标识，确保同一毫秒内的请求也有不同的 member
        long nanoTime = System.nanoTime() % 1000000; // 取后6位，确保唯一性
        long uniqueMember = currentTimeMs * 1000000 + nanoTime; // 组合成唯一标识
        long currentTime = currentTimeMs / 1000; // 秒级时间戳用于窗口计算
        // windowStart 计算说明：
        // 假设 currentTime = 100, windowSize = 60
        // 不加1: windowStart = 100 - 60 = 40，窗口范围 [40, 100] 包含 61 个时间点（40到100）
        // 加1:   windowStart = 100 - 60 + 1 = 41，窗口范围 [41, 100] 包含 60 个时间点（正好60秒）
        // 这样确保窗口大小严格等于 windowSize 秒，而不是 windowSize + 1 秒
        long windowStart = currentTime - config.getWindowSize() + 1;
        
        try (Jedis jedis = jedisPool.getResource()) {
            // 使用 Lua 脚本保证原子性
            // Lua 脚本在 Redis 中原子执行，避免并发问题
            String luaScript = 
                // ========== 第一部分：参数解析 ==========
                // KEYS[1]: Redis key（如 "ratelimiter:user123"）
                // ARGV[1]: windowStart - 窗口起始时间戳（秒）
                // ARGV[2]: currentTime - 当前时间戳（秒），用于窗口计算和作为 score
                // ARGV[3]: uniqueMember - 唯一标识（毫秒时间戳 + 纳秒后6位），作为唯一的 member
                // ARGV[4]: maxRequests - 最大允许请求数
                "local key = KEYS[1]\n" +
                "local windowStart = tonumber(ARGV[1])\n" +
                "local currentTime = tonumber(ARGV[2])\n" +
                "local uniqueMember = tonumber(ARGV[3])\n" +
                "local maxRequests = tonumber(ARGV[4])\n" +
                "\n" +
                // ========== 第二部分：清理过期数据 ==========
                // 使用 Sorted Set 存储请求时间戳
                // score = 秒级时间戳（用于窗口范围查询）
                // member = 毫秒级时间戳（确保唯一性，同一秒内的多次请求有不同的 member）
                // zremrangebyscore: 删除 score 在 [0, windowStart-1] 范围内的所有元素
                // 例如：windowStart = 41，则删除 score <= 40 的所有记录
                // 这样只保留窗口内的请求记录 [windowStart, currentTime]
                "-- 移除窗口外的旧记录\n" +
                "redis.call('zremrangebyscore', key, 0, windowStart - 1)\n" +
                "\n" +
                // ========== 第三部分：检查当前请求数 ==========
                // zcard: 获取 Sorted Set 中剩余元素的数量（即窗口内的请求数）
                // 由于已经删除了窗口外的记录，这里得到的就是当前窗口内的请求数
                "-- 获取当前窗口内的请求数\n" +
                "local currentCount = redis.call('zcard', key)\n" +
                "\n" +
                // ========== 第四部分：限流判断 ==========
                // 如果当前窗口内的请求数小于最大限制，允许请求
                "if currentCount < maxRequests then\n" +
                "    -- 允许请求，记录当前时间戳\n" +
                "    -- zadd: 将当前请求添加到 Sorted Set\n" +
                "    -- score = currentTime (秒级时间戳，用于窗口范围查询)\n" +
                "    -- member = uniqueMember (毫秒时间戳 + 纳秒后6位，确保唯一性)\n" +
                "    -- 使用 uniqueMember 作为 member，确保同一毫秒内的多次请求也有不同的 member\n" +
                "    -- 这样每次请求都会增加计数，而不是覆盖之前的记录\n" +
                "    redis.call('zadd', key, currentTime, uniqueMember)\n" +
                "    -- expire: 设置 key 的过期时间，避免内存泄漏\n" +
                "    -- 过期时间 = windowSize + 1 的原因：\n" +
                "    -- 1. 窗口内的数据需要保留 windowSize 秒\n" +
                "    -- 2. 额外+1秒作为缓冲，确保窗口滑动后，旧数据有足够时间被清理\n" +
                "    -- 3. 例如：windowSize=60，过期时间=61秒，即使没有新请求，key也会在61秒后自动删除\n" +
                "    -- 注意：这里的 + 号是 Java 字符串拼接，将 Lua 脚本字符串和计算结果拼接\n" +
                "    redis.call('expire', key, " + (config.getWindowSize() + 1) + ")\n" +
                "    return 1\n" +
                "else\n" +
                "    -- 超过限制，拒绝请求\n" +
                "    return 0\n" +
                "end";
            
            // eval 方法参数说明：
            // - script: Lua 脚本字符串
            // - keyCount: 指定前多少个参数是 KEYS[]（Redis key），剩余的是 ARGV[]（普通参数）
            //   这里 keyCount = 1，表示只有第一个参数是 key，后面的都是 ARGV
            // - key: KEYS[1] - Redis key（如 "ratelimiter:user123"）
            // - windowStart: ARGV[1] - 窗口起始时间戳
            // - currentTime: ARGV[2] - 当前时间戳（秒）
            // - uniqueMember: ARGV[3] - 唯一标识（毫秒时间戳 + 纳秒后6位）
            // - maxRequests: ARGV[4] - 最大允许请求数
            // 
            // 为什么需要 keyCount？
            // Redis 需要区分哪些参数是 KEYS（会被用于 Redis 集群的 key 路由），哪些是 ARGV（普通参数）
            // keyCount 告诉 Redis：前 keyCount 个参数是 KEYS[]，从第 keyCount+1 个开始都是 ARGV[]
            Object result = jedis.eval(luaScript, 1, key, 
                String.valueOf(windowStart), 
                String.valueOf(currentTime), 
                String.valueOf(uniqueMember),
                String.valueOf(config.getMaxRequests()));
            
            return ((Long) result).intValue() == 1;
        } catch (Exception e) {
            // 如果 Redis 连接失败，默认允许请求（降级策略）
            System.err.println("Redis connection error, allowing request: " + e.getMessage());
            return true;
        }
    }
    
    /**
     * 获取当前窗口内的请求数量
     * 
     * @param identifier 请求标识
     * @param config 限流配置
     * @return 当前窗口内的请求数
     */
    public long getCurrentCount(String identifier, RateLimitConfig config) {
        String key = keyPrefix + ":" + identifier;
        long currentTime = System.currentTimeMillis() / 1000;
        // 与 isAllowed 方法中的 windowStart 计算保持一致
        // 确保窗口大小严格等于 windowSize 秒
        long windowStart = currentTime - config.getWindowSize() + 1;
        
        try (Jedis jedis = jedisPool.getResource()) {
            // 移除窗口外的旧记录
            jedis.zremrangeByScore(key, 0, windowStart - 1);
            // 返回当前窗口内的请求数
            return jedis.zcard(key);
        } catch (Exception e) {
            System.err.println("Redis connection error: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * 重置指定标识的限流计数
     * 
     * @param identifier 请求标识
     */
    public void reset(String identifier) {
        String key = keyPrefix + ":" + identifier;
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
        } catch (Exception e) {
            System.err.println("Redis connection error: " + e.getMessage());
        }
    }
    
    /**
     * 关闭连接池
     */
    public void close() {
        if (jedisPool != null && !jedisPool.isClosed()) {
            jedisPool.close();
        }
    }
}

