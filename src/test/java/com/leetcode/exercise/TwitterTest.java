package com.leetcode.exercise;

import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

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
        Assert.that (feeds.size () == 1, "f" );

        twitter.follow (1,2);
        twitter.postTweet (2, 6);
        feeds = twitter.getNewsFeed (1);
        Assert.that (feeds.size () == 2, "f" );

        twitter.unfollow (1, 2);
        feeds = twitter.getNewsFeed (1);
        Assert.that (feeds.size () == 1, "f" );
    }

    @Test
    public void test2(){
        Twitter twitter = new Twitter ();
        twitter.postTweet (1,1);
        List<Integer> feeds = twitter.getNewsFeed (1);
        Assert.that (feeds.size () == 1, "f" );

        twitter.follow (2,1);
        feeds = twitter.getNewsFeed (2);
        Assert.that (feeds.size () == 1, "f" );

        twitter.unfollow (2, 1);
        feeds = twitter.getNewsFeed (2);
        Assert.that (feeds.size () == 0, "f" );
    }
}
