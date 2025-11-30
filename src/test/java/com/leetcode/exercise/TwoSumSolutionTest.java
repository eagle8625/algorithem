package com.leetcode.exercise;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

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
        assertTrue("failed", slices[0] == 0 && slices[1] == 1);
    }

    @Test
    public void twoListSumTest(){

        ListNode one1 = new ListNode (1);
        ListNode two1 = new ListNode (9);
        ListNode two2 = new ListNode (9);
        two1.next = two2;

        AddTwoNumbersSolution addTwoNumbersByList = new AddTwoNumbersSolution();
        ListNode root = addTwoNumbersByList.addTwoNumbers (one1, two1);
        System.out.println (root.val );
    }
}
