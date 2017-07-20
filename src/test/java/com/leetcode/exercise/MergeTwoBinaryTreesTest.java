package com.leetcode.exercise;

import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

/**
 * Created by liqingsong on 12/06/2017.
 */
public class MergeTwoBinaryTreesTest {
    @Test
    public void test1() {
        TreeNode t1 = new TreeNode (1);
        TreeNode t2 = new TreeNode (2);
        MergeTwoBinaryTrees mergeTwoBinaryTrees = new MergeTwoBinaryTrees ( );
        TreeNode treeNode = mergeTwoBinaryTrees.mergeTreesRecursion (t1, t2);
        Assert.that (treeNode.val == 3, "Merged failed");
    }

    @Test
    public void test2() {
        TreeNode t1 = new TreeNode (1);
        TreeNode left1 = new TreeNode (3);
        t1.left = left1;


        TreeNode t2 = new TreeNode (2);
        MergeTwoBinaryTrees mergeTwoBinaryTrees = new MergeTwoBinaryTrees ( );
        TreeNode treeNode = mergeTwoBinaryTrees.mergeTreesRecursion (t1, t2);
        Assert.that (treeNode.left.val == 3, "Merged failed");
    }


    @Test
    public void test3() {
        TreeNode t1 = new TreeNode (1);
        TreeNode left1 = new TreeNode (3);
        t1.left = left1;


        TreeNode t2 = new TreeNode (2);
        TreeNode left2 = new TreeNode (4);
        t2.left = left2;

        MergeTwoBinaryTrees mergeTwoBinaryTrees = new MergeTwoBinaryTrees ( );
        TreeNode treeNode = mergeTwoBinaryTrees.mergeTreesRecursion (t1, t2);
        Assert.that (treeNode.left.val == 7, "Merged failed");
    }

    @Test
    public void test4() {
        TreeNode t1 = new TreeNode (1);
        TreeNode left1 = new TreeNode (3);
        t1.left = left1;
        TreeNode right1 = new TreeNode (5);
        TreeNode right1left = new TreeNode (6);
        right1.left = right1left;
        t1.right = right1;

        TreeNode t2 = new TreeNode (2);
        TreeNode left2 = new TreeNode (4);
        t2.left = left2;

        MergeTwoBinaryTrees mergeTwoBinaryTrees = new MergeTwoBinaryTrees ( );
        TreeNode treeNode = mergeTwoBinaryTrees.mergeTreesRecursion (t1, t2);
        Assert.that (treeNode.right.left.val == 6, "Merged failed");
    }

}
