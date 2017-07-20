package com.leetcode.exercise;

/**
 * Created by liqingsong on 07/06/2017.
 */
public class MaxMultiple {
    public static int maxMultiple(int len) {
        if (len < 2) return 0;
        if (len == 2) return 1;
        if (len == 3) return 2;
        int[] products = new int[len + 1];
        products[0] = 0;
        products[1] = 1;
        products[2] = 2;
        products[3] = 3;
        int max = 0;
        int split = 0;
        for (int i = 4; i <= len; ++i) {
            max = 0;
            for (int j = 1; j <= i / 2; ++j) {
                if (max < products[j] * products[i - j]) {
                    max = products[j] * products[i - j];
                    split = j;
                }
            }
            System.out.printf ("len=%d, split: %d, %d, max=%d\n", i, split, i-split, max);
            products[i] = max;
        }
        return products[len];
    }
}
