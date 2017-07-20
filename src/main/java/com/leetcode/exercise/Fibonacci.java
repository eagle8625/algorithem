package com.leetcode.exercise;

/**
 * Created by liqingsong on 07/06/2017.
 */
public class Fibonacci {
    /**
     * inefficient because computing the same numbers again and again,
     * e.g. fibRecursive(9) = fibRecursive(8) + fibRecursive(7), every branch will compute fibRecursive(6),
     * fibRecursive(5)... repeatedly.
     *
     * @param n
     * @return
     */
    public static int fibRecursive(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibRecursive (n - 1) + fibRecursive (n - 2);
    }

    public static long fibLoop(int n){
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 1;
        long sn = 0, n1 = 1, n2 = 1;
        for(int i = 3; i <= n; i++ ){
            sn = n1 + n2;
            n1 = n2;
            n2 = sn;
        }
        return sn;
    }



    public static void main(String[] args) {
        for (int i = 0; i < 21; i++) {
            System.out.println (Fibonacci.fibRecursive (i));
        }

        for (int i = 0; i < 21; i++) {
            System.out.println (Fibonacci.fibLoop (i));
        }

        System.out.println (Fibonacci.fibLoop (100));
    }
}
