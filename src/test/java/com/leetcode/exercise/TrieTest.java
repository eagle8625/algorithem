package com.leetcode.exercise;

import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

/**
 * Created by liqingsong on 16/06/2017.
 */
public class TrieTest {
    @Test
    public void test1(){
        Trie trie = new Trie ();
        trie.insert ("abc");
        boolean res = trie.search ("abc");
        Assert.that (res == true, "failed" );

        res = trie.startsWith ("ab");
        Assert.that (res == true, "failed" );

        res = trie.startsWith ("bc");
        Assert.that (res == false, "failed" );
    }

    @Test
    public void test2(){
        WordDictionary wordDictionary = new WordDictionary ();
        wordDictionary.addWord ("bad");
        wordDictionary.addWord ("dad");
        wordDictionary.addWord ("mad");

        Assert.that (wordDictionary.search ("pad") == false, "w");
        Assert.that (wordDictionary.search ("bad") == true, "w");
        Assert.that (wordDictionary.search (".ad") == true, "w");
        Assert.that (wordDictionary.search ("b..") == true, "w");

        Assert.that (wordDictionary.search (".") == false, "w");
    }
}
