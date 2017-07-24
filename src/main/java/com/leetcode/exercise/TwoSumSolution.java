package com.leetcode.exercise;

/**
 * Created by liqingsong on 24/07/2017.
 * https://leetcode.com/problems/two-sum/#/description
 * Given an array of int number and a target number, return indices of
 * the two numbers which are added up to the target
 */

public class TwoSumSolution {
    /**
     * A brute force method with O(n*n) time complexity.
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target){
        int[] slices = new int[2];
        if(nums == null || nums.length < 2){
            return slices;
        }

        for(int i = 0; i < nums.length; i++){
            for(int j = i + 1; j < nums.length; j++){
                if(nums[i] + nums[j] == target){
                    slices[0] = i;
                    slices[1] = j;
                    return slices;
                }
            }
        }
        return slices;
    }

}
