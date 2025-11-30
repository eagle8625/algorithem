package com.leetcode.exercise;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by liqingsong on 15/06/2017.
 */
public class LRUCacheTest {
    @Test
    public void test1(){
        LRUCache cache = new LRUCache (2);
        cache.put (1,1);
        cache.put (2,2);
        int v = cache.get (1);
        assertEquals("get(1) should return 1", 1, v);
        cache.put (3,3);
        v = cache.get (2);
        assertEquals("get(2) should return -1 after eviction", -1, v);
        cache.put (4,4);
        v = cache.get (1);
        assertEquals("get(1) should return -1 after eviction", -1, v);
        v = cache.get (3);
        assertEquals("get(3) should return 3", 3, v);
        v = cache.get (4);
        assertEquals("get(4) should return 4", 4, v);
    }
}
