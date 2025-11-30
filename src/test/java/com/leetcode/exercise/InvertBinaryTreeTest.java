package com.leetcode.exercise;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by liqingsong on 08/06/2017.
 */
public class InvertBinaryTreeTest {
    @Test
    public void test2(){
        TreeNode leftNode1 = new TreeNode (1 );
        leftNode1.setVal (1);

        TreeNode rightNode1 = new TreeNode ( 3);
        rightNode1.setVal (3);

        TreeNode rootNode1 = new TreeNode ( 2);
        rootNode1.setVal (2);
        rootNode1.setLeft (leftNode1);
        rootNode1.setRight (rightNode1);

        leftNode1.setParent (rootNode1);
        rightNode1.setParent (rootNode1);

        TreeNode leftNode2 = new TreeNode ( 6);
        leftNode2.setVal (6);

        TreeNode rightNode2 = new TreeNode (9 );
        rightNode2.setVal (9);

        TreeNode rootNode2 = new TreeNode ( 7);
        rootNode2.setVal (7);
        rootNode2.setLeft (leftNode2);
        rootNode2.setRight (rightNode2);
        leftNode2.setParent (rootNode2);
        rightNode2.setParent (rootNode2);

        TreeNode rootNode = new TreeNode ( 4);
        rootNode.setVal (4);
        rootNode.setLeft (rootNode1);
        rootNode.setRight (rootNode2);

        rootNode1.setParent (rootNode);
        rootNode2.setParent (rootNode);

        rootNode = InvertBinaryTree.invertTree (rootNode);

        assertEquals("invert failed", 7, rootNode.getLeft().getVal());
    }
}
