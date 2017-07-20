package com.leetcode.exercise;

import sun.jvm.hotspot.utilities.Assert;

/**
 * Created by liqingsong on 13/06/2017.
 * Lowest common ancestor with lagest depth ancestor node for both nodes
 */
public class LCA {
    /**
     * @param root: The root of the binary search tree.
     * @param A     and B: two nodes in a Binary.
     * @return: Return the least common ancestor(LCA) of the two nodes.
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode A, TreeNode B) {
        if (root == null) return null;
        if (root == B || root == A) return root;
        TreeNode left = lowestCommonAncestor (root.left, A, B);
        TreeNode right = lowestCommonAncestor (root.right, A, B);

        if (right != null && left != null ) return root;
        return  left != null ? left : right;
    }

    @org.junit.Test
    public void test1() {
        TreeNode root = new TreeNode (4);

        TreeNode left1 = new TreeNode (3);

        TreeNode right1 = new TreeNode (7);
        TreeNode right2 = new TreeNode (5);
        TreeNode right3 = new TreeNode (6);

        right1.left = right2;
        right1.right = right3;

        root.left = left1;
        root.right = right1;
        LCA lca = new LCA ( );
        TreeNode treeNode = lca.lowestCommonAncestor (root, left1, right3);

        Assert.that (treeNode.equals (root), "LCA failed");
    }

    @org.junit.Test
    public void test2() {
        TreeNode root = new TreeNode (4);

        TreeNode left1 = new TreeNode (3);

        TreeNode right1 = new TreeNode (7);
        TreeNode right2 = new TreeNode (5);
        TreeNode right3 = new TreeNode (6);

        right1.left = right2;
        right1.right = right3;

        root.left = left1;
        root.right = right1;
        LCA lca = new LCA ( );
        TreeNode treeNode = lca.lowestCommonAncestor (root, right2, right3);

        Assert.that (treeNode.equals (right1), "LCA failed");
    }

    @org.junit.Test
    public void test3() {
        TreeNode root = new TreeNode (4);

        TreeNode left1 = new TreeNode (3);

        TreeNode right1 = new TreeNode (7);
        TreeNode right2 = new TreeNode (5);
        TreeNode right3 = new TreeNode (6);

        right1.left = right2;
        right1.right = right3;

        root.left = left1;
        root.right = right1;
        LCA lca = new LCA ( );
        TreeNode treeNode = lca.lowestCommonAncestor (root, right3, right1);

        Assert.that (treeNode.equals (right1), "LCA failed");
    }

    @org.junit.Test
    public void test4() {
        TreeNode root = new TreeNode (1);

        TreeNode right1 = new TreeNode (2);
        TreeNode right2 = new TreeNode (3);
        TreeNode right3 = new TreeNode (4);
        TreeNode right4 = new TreeNode (5);

        right3.right = right4;
        right2.right = right3;
        right1.right = right2;
        root.right = right1;
        LCA lca = new LCA ( );
        TreeNode treeNode = lca.lowestCommonAncestor (root, right2, right4);

        Assert.that (treeNode.equals (right2), "LCA failed");
    }
}
