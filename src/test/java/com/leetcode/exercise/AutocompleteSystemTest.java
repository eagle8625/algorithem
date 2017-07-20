package com.leetcode.exercise;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by liqingsong on 20/07/2017.
 */
public class AutocompleteSystemTest {

    @Test
    public void autocompleteTest(){
        String[] sentences = {"i love you", "island", "ironman", "i love leetcode"};
        int[] times = {5,3,2,2};
        AutocompleteSystem autocompleteSystem = new AutocompleteSystem (sentences, times);
        List list = autocompleteSystem.input ('i');
        Assert.assertTrue ("failed", list.get (0).equals ("i love you") );

        list = autocompleteSystem.input (' ');
        Assert.assertTrue ("failed", list.get (1).equals ("i love leetcode") );

        list = autocompleteSystem.input ('a');
        Assert.assertTrue ("failed", list.size () == 0 );

        list = autocompleteSystem.input ('#');
        Assert.assertTrue ("failed", list.size () == 0 );

        Assert.assertTrue ("failed", autocompleteSystem.getMap ().size () == 5);

    }
}
