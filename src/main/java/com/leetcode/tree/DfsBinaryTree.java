package com.leetcode.tree;

import com.leetcode.common.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * deep-first-search for binary tree
 *
 * @author liqingsong05
 * @date 2020/10/7 11:20 PM
 */
public class DfsBinaryTree {
	public static List<List<Integer>> allPath = new ArrayList<>();

	public static void dfs(Node treeNode) {
		List<Integer> list = new ArrayList<>();
		list.add(treeNode.val);
		if (null != treeNode.left) {
			iterateTree(treeNode.left, list);
		}

		List<Integer> rightlist = new ArrayList<>();
		rightlist.add(treeNode.val);
		if (null != treeNode.right) {
			iterateTree(treeNode.right, rightlist);
		}
	}

	public static void iterateTree(Node node, List<Integer> list) {
		list.add(node.val);
		List<Integer> parentList = new ArrayList<>();
		parentList.addAll(list);
		if (null != node.left) {
			iterateTree(node.left, list);
		}

		if (null != node.right) {
			iterateTree(node.right, parentList);
		}
		if (null == node.left && null == node.right) {
			allPath.add(list);
		}
	}

	public static void main(String[] args) {
		Node root = new Node();
		root.val = 1;
		Node left = new Node();
		left.val = 2;
		Node right = new Node();
		right.val = 3;
		root.left = left;
		root.right = right;

		Node rightLeft = new Node();
		rightLeft.val = 4;
		right.left = rightLeft;

		Node rightRight = new Node();
		rightRight.val = 5;
		right.right = rightRight;

		dfs(root);
		System.out.println(allPath);
	}
}
