package com.leetcode.exercise;

/**
 * Created by liqingsong on 12/06/2017.
 */
public class MergeTwoBinaryTrees {

    public TreeNode mergeTreesRecursion(TreeNode t1, TreeNode t2) {
        if (t1 == null) return t2;
        if (t2 == null) return t1;

        TreeNode mergedRootNode = new TreeNode (t1.val + t2.val);
        TreeNode leftMergedNode = mergeTreesRecursion (t1.left, t2.left);
        TreeNode rightMergedNode = mergeTreesRecursion (t1.right, t2.right);
        mergedRootNode.left = leftMergedNode;
        mergedRootNode.right = rightMergedNode;
        return mergedRootNode;
    }
}
