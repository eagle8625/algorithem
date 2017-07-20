package com.leetcode.exercise;

/**
 * Created by liqingsong on 08/06/2017.
 */
public class ConstructStringOfBinaryTree {


    public String tree2str(TreeNode t) {
        String str = innertree2str (t);

        while (str.contains ("())")) {
            str = str.replaceAll ("\\(\\)\\)", ")");
        }
        while (str.endsWith ("()")) {
            str = str.substring (0, str.length () - 2);
        }
        return str;
    }

    public String innertree2str(TreeNode t){
        String str = "";
        if (t == null) {
            str += "()";
        } else {
            str += t.val;
            str += "(" + tree2str (t.left) + ")";
            str += "(" + tree2str (t.right) + ")";
        }
        return str;
    }

    public String answer1(TreeNode t){
        if(t == null) return "";
        if(t.left == null && t.right == null)
            return t.val + "";
        if(t.right == null)
            return t.val + "(" + answer1(t.left) + ")";
        return t.val +  "(" + answer1(t.left) + ")";
    }

}
