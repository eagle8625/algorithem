package com.leetcode.exercise;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.List;

/**
 * Created by liqingsong on 17/06/2017.
 */
public class TwitterTest {

    @Test
    public void test1(){
        Twitter twitter = new Twitter ();
        twitter.postTweet (1,5);
        List<Integer> feeds = twitter.getNewsFeed (1);
        assertEquals("f", 1, feeds.size());

        twitter.follow (1,2);
        twitter.postTweet (2, 6);
        feeds = twitter.getNewsFeed (1);
        assertEquals("f", 2, feeds.size());

        twitter.unfollow (1, 2);
        feeds = twitter.getNewsFeed (1);
        assertEquals("f", 1, feeds.size());
    }

    @Test
    public void test2(){
        Twitter twitter = new Twitter ();
        twitter.postTweet (1,1);
        List<Integer> feeds = twitter.getNewsFeed (1);
        assertEquals("f", 1, feeds.size());

        twitter.follow (2,1);
        feeds = twitter.getNewsFeed (2);
        assertEquals("f", 1, feeds.size());

        twitter.unfollow (2, 1);
        feeds = twitter.getNewsFeed (2);
        assertEquals("f", 0, feeds.size());
    }
}
