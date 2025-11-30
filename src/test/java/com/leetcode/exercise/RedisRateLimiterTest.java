package com.leetcode.exercise;

import com.leetcode.exercise.RedisRateLimiter.RateLimitConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Redis 限流器测试类
 * 
 * 注意：运行此测试前需要确保 Redis 服务已启动
 */
public class RedisRateLimiterTest {
    
    private RedisRateLimiter rateLimiter;
    
    @Before
    public void setUp() {
        // 使用默认配置连接本地 Redis
        rateLimiter = new RedisRateLimiter("localhost", 6379, "test:ratelimiter");
    }
    
    @After
    public void tearDown() {
        if (rateLimiter != null) {
            rateLimiter.close();
        }
    }
    
    @Test
    public void testBasicRateLimit() {
        String userId = "user123";
        RateLimitConfig config = new RateLimitConfig(60, 10); // 60秒内最多10个请求
        
        // 先重置，确保测试环境干净
        rateLimiter.reset(userId);
        
        // 前10个请求应该被允许
        for (int i = 0; i < 10; i++) {
            boolean allowed = rateLimiter.isAllowed(userId, config);
            assertTrue("Request " + (i + 1) + " should be allowed", allowed);
            // 验证计数
            long count = rateLimiter.getCurrentCount(userId, config);
            assertEquals("Count after request " + (i + 1) + " should be " + (i + 1), 
                i + 1, count);
        }
        
        // 验证第10个请求后的计数
        long countAfter10 = rateLimiter.getCurrentCount(userId, config);
        assertEquals("Count after 10 requests should be 10", 10, countAfter10);
        
        // 第11个请求应该被拒绝
        boolean allowed11 = rateLimiter.isAllowed(userId, config);
        assertFalse("Request 11 should be rejected, but was allowed. Count: " + 
            rateLimiter.getCurrentCount(userId, config), allowed11);
        
        // 检查当前计数应该仍然是10（因为第11个请求被拒绝，没有添加）
        long count = rateLimiter.getCurrentCount(userId, config);
        assertEquals("Current count should still be 10 after rejected request", 10, count);
    }
    
    @Test
    public void testDifferentUsers() {
        RateLimitConfig config = new RateLimitConfig(60, 5);
        
        // 不同用户的限流应该是独立的
        assertTrue(rateLimiter.isAllowed("user1", config));
        assertTrue(rateLimiter.isAllowed("user2", config));
        assertTrue(rateLimiter.isAllowed("user1", config));
        assertTrue(rateLimiter.isAllowed("user2", config));
        
        // 每个用户都应该有自己的计数
        assertEquals(2, rateLimiter.getCurrentCount("user1", config));
        assertEquals(2, rateLimiter.getCurrentCount("user2", config));
    }
    
    @Test
    public void testReset() {
        String userId = "user_reset";
        RateLimitConfig config = new RateLimitConfig(60, 5);
        
        // 使用5次
        for (int i = 0; i < 5; i++) {
            rateLimiter.isAllowed(userId, config);
        }
        
        assertEquals(5, rateLimiter.getCurrentCount(userId, config));
        assertFalse(rateLimiter.isAllowed(userId, config));
        
        // 重置后应该可以继续使用
        rateLimiter.reset(userId);
        assertEquals(0, rateLimiter.getCurrentCount(userId, config));
        assertTrue(rateLimiter.isAllowed(userId, config));
    }
    
    @Test
    public void testSlidingWindow() throws InterruptedException {
        String userId = "user_sliding";
        RateLimitConfig config = new RateLimitConfig(2, 3); // 2秒内最多3个请求
        
        // 快速发送3个请求
        assertTrue(rateLimiter.isAllowed(userId, config));
        assertTrue(rateLimiter.isAllowed(userId, config));
        assertTrue(rateLimiter.isAllowed(userId, config));
        assertFalse(rateLimiter.isAllowed(userId, config));
        
        // 等待2秒后，窗口滑动，应该可以继续请求
        Thread.sleep(2100);
        assertTrue("Should allow request after window slides", 
            rateLimiter.isAllowed(userId, config));
    }
    
    @Test
    public void testHighFrequencyRequests() {
        String userId = "user_high_freq";
        RateLimitConfig config = new RateLimitConfig(10, 100); // 10秒内最多100个请求
        
        int allowedCount = 0;
        int rejectedCount = 0;
        
        // 快速发送150个请求
        for (int i = 0; i < 150; i++) {
            if (rateLimiter.isAllowed(userId, config)) {
                allowedCount++;
            } else {
                rejectedCount++;
            }
        }
        
        // 应该允许100个，拒绝50个
        assertEquals("Should allow exactly 100 requests", 100, allowedCount);
        assertEquals("Should reject 50 requests", 50, rejectedCount);
    }
}

