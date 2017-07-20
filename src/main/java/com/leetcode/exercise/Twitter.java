package com.leetcode.exercise;

import java.util.*;

/**
 * Created by liqingsong on 16/06/2017.
 */
public class Twitter {
    private HashMap<Integer, User> userHashMap;
    private int timestamp;

    public class User {
        public int userId;
        public Set<User> followees;

        public User(int userId) {
            this.userId = userId;
            followees = new HashSet<> ( );
            followees.add (this);
        }

        public Tweet head;

        public void addTweet(Tweet tweet) {
            tweet.next = head;
            head = tweet;
        }
    }

    public class Tweet {
        public int timestamp;
        public String text;
        public int tweetId;
        public Tweet next;
    }

    /**
     * Initialize your data structure here.
     */
    public Twitter() {
        userHashMap = new HashMap<> ( );
    }

    /**
     * Compose a new tweet.
     */
    public void postTweet(int userId, int tweetId) {
        User user = userHashMap.get (userId);
        if (user == null) user = new User (userId);
        this.timestamp += 1;
        Tweet tweet = new Tweet ( );
        tweet.tweetId = tweetId;
        tweet.timestamp = this.timestamp;
        user.addTweet (tweet);
        userHashMap.put (userId, user);
    }


    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed
     * must be posted by users who the user followed or by the user herself. Tweets must be
     * ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {
        User user = userHashMap.get (userId);
        List<Integer> list = new ArrayList<> ( );
        if (user == null) return list;

        int i = 0;
        PriorityQueue<Tweet> queue = new PriorityQueue<Tweet> (10,
                (a, b) -> (b.timestamp - a.timestamp));
        for (User tmpUser : user.followees) {
            if (i < 10) {
                if (tmpUser.head != null) {
                    queue.add (tmpUser.head);
                    i += 1;
                }
            } else {
                break;
            }
        }

        Tweet tweet;
        while (list.size ( ) < 10 && !queue.isEmpty ( )) {
            tweet = queue.poll ( );
            list.add (tweet.tweetId);
            if (tweet.next != null) queue.add (tweet.next);
        }
        return list;
    }

    /**
     * Follower follows a followee. If the operation is invalid, it should be a no-op.
     */
    public void follow(int followerId, int followeeId) {
        User follower = userHashMap.get (followerId);
        if (follower == null) {
            follower = new User (followerId);
            userHashMap.put (followerId, follower);
        }

        User followee = userHashMap.get (followeeId);
        if (followee == null) {
            followee = new User (followeeId);
            userHashMap.put (followeeId, followee);
        }

        follower.followees.add (followee);
    }

    /**
     * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
     */
    public void unfollow(int followerId, int followeeId) {
        if(followerId == followeeId) return;
        User follower = userHashMap.get (followerId);
        if (follower == null) return;

        User followee = userHashMap.get (followeeId);
        if (followee == null) return;

        follower.followees.remove (followee);
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
