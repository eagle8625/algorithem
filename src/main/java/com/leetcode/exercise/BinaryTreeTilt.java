package com.leetcode.exercise;

/**
 * Created by liqingsong on 07/06/2017.
 */
public class BinaryTreeTilt {
    public  int tilt = 0;

    public  int tilt(TreeNode treeNode) {
        if (treeNode == null) return 0;
        TreeNode leftNode = treeNode.getLeft ( );
        TreeNode rightNode = treeNode.getRight ( );
        int leftValue = tilt (leftNode);
        int rightValue = tilt (rightNode);
        tilt += Math.abs (leftValue - rightValue);
        return treeNode.getVal ( ) + leftValue + rightValue;
    }
}

