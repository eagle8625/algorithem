package com.leetcode.exercise;

import org.junit.Test;

/**
 * Created by liqingsong on 08/06/2017.
 */
public class ConstructBSTForSortedArrayTest {
    @Test
    public void test1(){
        int[] in = {3,4,5,6,7,8,9,10,12,13};
        ConstructBSTForSortedArray constructBSTForSortedArray = new ConstructBSTForSortedArray ();
        TreeNode treeNode = constructBSTForSortedArray.bst (in);
        System.out.println (treeNode.val );
    }
}
