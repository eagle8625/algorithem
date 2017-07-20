package com.leetcode.exercise;

import java.util.Stack;

/**
 * Created by liqingsong on 12/06/2017.
 */
public class StringReverse {
    public String reverseWords(String s) {
        // write your code
        if (s == null) return null;
        s = s.trim ( );
        Stack stack = new Stack ( );
        char c;
        for (int i = 0, j = 0; i < s.length ( ); i++) {
            c = s.charAt (i);
            if (c == ' ' || i == s.length ( ) - 1) {
                if (!s.substring (j, i).equals (" ")) {
                    if (i != s.length ( ) - 1) {
                        stack.push (s.substring (j, i));
                    }else{
                        stack.push (s.substring (j, i+1));
                    }
                    j = i + 1;
                }
            } else {
            }
        }
        StringBuilder sbuf = new StringBuilder ( );
        while (!stack.empty ( )) {
            sbuf.append (stack.pop ( ));
            sbuf.append (" ");
        }
        return sbuf.toString ( );
    }

    /**
     *@param nums: The integer array you should partition
     *@param k: As description
     *return: The index after partition
     */
    public int partitionArray(int[] nums, int k) {
        //write your code here
        int[] biggerOrEqual = new int[nums.length];
        int[] smaller = new int[nums.length];
        int p = 0, q = 0;
        for(int i = 0; i < nums.length; i++){
            if(nums[i] >= k) biggerOrEqual[q++] = nums[i];
            else smaller[p++] = nums[i];
        }

        int[] partioned = new int[nums.length];
        System.arraycopy (smaller, 0, partioned, 0, p);
        System.arraycopy (biggerOrEqual, 0, partioned, p, q);

        return p;
    }

    public static void main(String... args) {
        StringReverse stringReverse = new StringReverse ( );
//        String str = stringReverse.reverseWords ("the sky is blue");
//        System.out.println (str);
        int[] in = {3,2,2,1};
        System.out.println (stringReverse.partitionArray (in, 2) );
    }
}

