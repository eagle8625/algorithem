package com.leetcode.exercise;

/**
 * Created by liqingsong on 08/06/2017.
 */
public class ConstructBSTForSortedArray {
    public TreeNode bst(int[] sorted) {
        if (sorted == null) return null;
        int i = sorted.length / 2;

        TreeNode treeNode = new TreeNode (sorted[i]);

        int[] left;
        int[] right;
        if (sorted.length % 2 == 0) {
            int leftlen = i;
            left = new int[leftlen];
            right = new int[i - 1];
        } else {
            left = new int[i];
            right = new int[i];
        }
        if (left != null && left.length > 0) {
            System.arraycopy (sorted, 0, left, 0, left.length);
            TreeNode leftNode = bst (left);
            treeNode.left = leftNode;
        }
        if (right != null && right.length > 0) {
            System.arraycopy (sorted, i + 1, right, 0, right.length);
            TreeNode rightNode = bst (right);
            treeNode.right = rightNode;
        }
        return treeNode;
    }


}
