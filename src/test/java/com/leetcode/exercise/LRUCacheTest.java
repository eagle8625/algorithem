package com.leetcode.exercise;

import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

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
        Assert.that (v == 1, "failed" );
        cache.put (3,3);
        v = cache.get (2);
        Assert.that (v == -1, "failed" );
        cache.put (4,4);
        v = cache.get (1);
        Assert.that (v == -1, "failed" );
        v = cache.get (3);
        Assert.that (v == 3, "failed" );
        v = cache.get (4);
        Assert.that (v == 4, "failed" );
    }
}
