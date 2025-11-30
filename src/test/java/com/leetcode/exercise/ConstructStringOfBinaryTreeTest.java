package com.leetcode.exercise;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by liqingsong on 08/06/2017.
 */
public class ConstructStringOfBinaryTreeTest {
    @Test
    public void test2(){
        TreeNode leftNode1 = new TreeNode (4 );

        TreeNode rootNode1 = new TreeNode ( 2);
        rootNode1.setLeft (leftNode1);


        TreeNode rootNode = new TreeNode ( 1);
        rootNode.setLeft (rootNode1);

        TreeNode rightNode2 = new TreeNode (3 );
        rootNode.right =  rightNode2;

        ConstructStringOfBinaryTree constructStringOfBinaryTree = new ConstructStringOfBinaryTree();

        String str = constructStringOfBinaryTree.tree2str (rootNode);
        System.out.println (str );
        assertEquals("construct binary tree pre-order string failed", "1(2(4))(3)", str);
    }

    @Test
    public void test3(){
        TreeNode rightNode1 = new TreeNode (4 );

        TreeNode rootNode1 = new TreeNode ( 2);
        rootNode1.right = rightNode1;


        TreeNode rootNode = new TreeNode ( 1);
        rootNode.setLeft (rootNode1);

        TreeNode rightNode2 = new TreeNode (3 );
        rootNode.right =  rightNode2;

        ConstructStringOfBinaryTree constructStringOfBinaryTree = new ConstructStringOfBinaryTree();

        String str = constructStringOfBinaryTree.tree2str (rootNode);
        System.out.println (str );
        assertEquals("construct binary tree pre-order string failed", "1(2()(4))(3)", str);
    }

    @Test
    public void test4(){
        TreeNode leftNode1 = new TreeNode (4 );
        TreeNode leftNode2 = new TreeNode (2 );
        TreeNode leftNode3 = new TreeNode (3 );

        leftNode2.left = leftNode3;
        leftNode2.right = leftNode1;


        TreeNode rootNode = new TreeNode ( 1);
        rootNode.left = leftNode2;

        ConstructStringOfBinaryTree constructStringOfBinaryTree = new ConstructStringOfBinaryTree();

        String str = constructStringOfBinaryTree.tree2str (rootNode);
        System.out.println (str );
        assertEquals("construct binary tree pre-order string failed", "1(2(3)(4))", str);
    }

    @Test
    public void test5(){
        ConstructStringOfBinaryTree constructStringOfBinaryTree = new ConstructStringOfBinaryTree();

        String str = constructStringOfBinaryTree.tree2str (null);
        System.out.println (str );
        assertEquals("construct binary tree pre-order string failed", "", str);
    }

    @Test
    public void test6(){
        TreeNode root = new TreeNode (3);
        TreeNode left1 = new TreeNode (1);
        TreeNode left2 = new TreeNode (2);
        left1.right = left2;
        root.left = left1;
        ConstructStringOfBinaryTree constructStringOfBinaryTree = new ConstructStringOfBinaryTree();
        String str = constructStringOfBinaryTree.tree2str (root);
        System.out.println (str);
        assertEquals("construct binary tree pre-order string failed", "3(1()(2))", str);

    }
}
