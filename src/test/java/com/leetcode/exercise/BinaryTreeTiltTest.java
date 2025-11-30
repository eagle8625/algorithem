package com.leetcode.exercise;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by liqingsong on 07/06/2017.
 */
public class BinaryTreeTiltTest {
    @Test
    public void test1(){
        TreeNode leftNode = new TreeNode ( 2);

        TreeNode rightNode = new TreeNode (3 );

        TreeNode rootNode = new TreeNode ( 1);
        rootNode.setLeft (leftNode);
        rootNode.setRight (rightNode);
        BinaryTreeTilt binaryTreeTilt = new BinaryTreeTilt();
        binaryTreeTilt.tilt (rootNode);
        assertEquals("tilt failed", 1, binaryTreeTilt.tilt);
    }

    @Test
    public void test2(){
        TreeNode leftNode1 = new TreeNode ( 3);

        TreeNode rightNode1 = new TreeNode (1 );

        TreeNode rootNode1 = new TreeNode ( 2);
        rootNode1.setLeft (leftNode1);
        rootNode1.setRight (rightNode1);

        TreeNode leftNode2 = new TreeNode ( 6);

        TreeNode rightNode2 = new TreeNode (5 );

        TreeNode rootNode2 = new TreeNode (4 );
        rootNode2.setLeft (leftNode2);
        rootNode2.setRight (rightNode2);

        TreeNode rootNode = new TreeNode ( 3);
        rootNode.setLeft (rootNode1);
        rootNode.setRight (rootNode2);

        BinaryTreeTilt binaryTreeTilt = new BinaryTreeTilt();
        binaryTreeTilt.tilt (rootNode);

        assertEquals("tilt failed", 12, binaryTreeTilt.tilt);
    }
}
