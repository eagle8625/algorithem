package com.leetcode.exercise;

/**
 * Created by liqingsong on 16/06/2017.
 */
public class TrieNode {
    TrieNode parent;
    TrieNode[] children;
    Character value;
    int childrenNum;
    //true: word end; false: not word end
    boolean flag;
    String item = "";
    int amount;
}