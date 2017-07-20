package com.leetcode.exercise;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by liqingsong on 16/06/2017.
 */
public class WordSearchTest {
    @Test
    public void test1(){
        WordSearch wordSearch = new WordSearch ();
        char[][] board = { {'o','a','a','n'},
                {'e','t','a','e'},
                {'i','h','k','r'},
                {'i','f','l','v'}};
        String[] words = {"oath","pea","eat","rain"};
        List<String> list = wordSearch.findWords (board, words);
        Assert.assertTrue ("fail", list!= null && list.size ()==2);
    }
}
