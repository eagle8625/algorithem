package com.leetcode.exercise;

/**
 * Created by liqingsong on 08/06/2017.
 */
public class InvertBinaryTree {
    public static TreeNode invertTree(TreeNode root) {
        if (root == null || root.getLeft ( ) == null && root.getRight ( ) == null) return
                root;
        TreeNode left = invertTree (root.getLeft ());
        TreeNode right = invertTree (root.getRight ());
        TreeNode tmp = left;
        root.setLeft (root.getRight ());
        root.setRight (tmp);
        return root;
//        Stack<TreeNode> stack = new Stack<> ( );
//        TreeNode left = root.getLeft ( );
//        if (left != null) {
//            while (left.getLeft ( ) != null) {
//                left = left.getLeft ( );
//            }
//            while (left != root) {
//                stack.push (left);
//                if (left.getRight ( ) != null) {
//                    stack.push (invert (left.getRight ( )));
//                }
//                left = left.getParent ( );
//            }
//        }
//
//        stack.push (root);
//        TreeNode right = root.getRight ( );
//        if (right != null) {
//            while (right.getLeft ( ) != null) {
//                right = right.getLeft ( );
//            }
//            while (right != root) {
//                stack.push (right);
//                if (right.getRight ( ) != null) {
//                    stack.push(invert (right.getRight ( )));
//                }
//                right = right.getParent ( );
//            }
//        }
//
//
//        while (!stack.empty ( )) {
//            TreeNode treeNode = stack.pop ( );
//            if (treeNode.getLeft ( ) != null) {
//                if (treeNode.getRight ( ) != null) {
//                    TreeNode tmp = treeNode.getLeft ( );
//                    treeNode.setLeft (treeNode.getRight ( ));
//                    treeNode.setRight (tmp);
//                } else {
//                    treeNode.setRight (treeNode.getLeft ( ));
//                }
//            } else {
//                if (treeNode.getRight ( ) != null) {
//                    treeNode.setLeft (treeNode.getRight ( ));
//                }
//            }
//            System.out.println (treeNode.getVal ( ));
//        }
//        return root;

    }
}

