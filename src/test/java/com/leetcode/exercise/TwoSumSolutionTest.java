package com.leetcode.exercise;

import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

/**
 * Created by liqingsong on 24/07/2017.
 */
public class TwoSumSolutionTest {
    @Test
    public void twoSumTest(){
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        TwoSumSolution solution = new TwoSumSolution ();
        int[] slices = solution.twoSum (nums, target);
        Assert.that (slices[0] == 0 && slices[1] == 1, "failed" );
    }
}
