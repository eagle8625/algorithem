package com.leetcode.exercise;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by liqingsong on 16/06/2017.
 */
public class TrieTest {
    @Test
    public void test1(){
        Trie trie = new Trie ();
        trie.insert ("abc");
        boolean res = trie.search ("abc");
        assertTrue("failed", res);

        res = trie.startsWith ("ab");
        assertTrue("failed", res);

        res = trie.startsWith ("bc");
        assertFalse("failed", res);
    }

    @Test
    public void test2(){
        WordDictionary wordDictionary = new WordDictionary ();
        wordDictionary.addWord ("bad");
        wordDictionary.addWord ("dad");
        wordDictionary.addWord ("mad");

        assertFalse("w", wordDictionary.search("pad"));
        assertTrue("w", wordDictionary.search("bad"));
        assertTrue("w", wordDictionary.search(".ad"));
        assertTrue("w", wordDictionary.search("b.."));
        assertFalse("w", wordDictionary.search("."));
    }
}
