package com.leetcode.exercise;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class RedisSortedTest {
    private RedisSorted sorted;

    @Before
    public void setUp() throws Exception {
        sorted = new RedisSorted();
    }

    @After
    public void tearDown() throws Exception {
        sorted = null;
    }

    @Test
    public void addVisit() {
        sorted.remove();
        for (int i = 0; i < 10; i++) {
            sorted.addVisit("user0");
        }

        for (int i = 0; i < 40; i++) {
            sorted.addVisit("user1");
        }

        for (int i = 0; i < 30; i++) {
            sorted.addVisit("user2");
        }
    }

    @Test
    public void sortedVisit() {
        Map<String, Long> stringLongMap = sorted.sortedVisit();
        System.out.println(stringLongMap);

    }
}