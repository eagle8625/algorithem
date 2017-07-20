package com.leetcode.exercise;

/**
 * Created by liqingsong on 08/06/2017.
 */
class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode parent;
    String key;

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}
