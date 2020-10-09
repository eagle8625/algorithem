package com.leetcode.exercise;

import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;

/**
 * Created by liqingsong on 13/06/2017.
 * This is dynamic programming algorithm, DP.
 */
public class MinExchange {
    /**
     * Great sample for concise. Generate minimum for the given value and the money kinds
     *
     * @param coinsValues coins type array
     * @param amount      to exchange
     * @return
     */
    public int minimumChange111(int[] coinsValues, int amount) {
        int max = amount + 1;
        int[] dp = new int[max];
        Arrays.fill (dp, max);

        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int kind = 0; kind < coinsValues.length; kind++) {
                if (i >= coinsValues[kind]) {
                    dp[i] = Math.min (dp[i], dp[i - coinsValues[kind]] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    /**
     * why is it wrong?
     * @param coinsValues
     * @param amount
     * @return
     */
    public int minimumChange(int[] coinsValues, int amount) {
        int[][] change = new int[coinsValues.length + 1][amount + 1];
        for (int i = 0; i <= coinsValues.length; i++) {
            change[i][0] = 0;
        }

        for (int i = 0; i <= amount; i++) {
            change[0][i] = Integer.MAX_VALUE;
        }

        for (int v = 1; v <= amount; v++) {
            for (int c = 1; c <= coinsValues.length; c++) {
                if (coinsValues[c - 1] > v) {
                    change[c][v] = change[c - 1][v];
                    break;
                }
                if (change[c - 1][v] > change[c][v - coinsValues[c - 1]] + 1) {
                    change[c][v] = change[c][v - coinsValues[c - 1]] + 1;
                } else {
                    change[c][v] = change[c - 1][v];
                }
            }
        }
        return change[coinsValues.length][amount] == Integer.MAX_VALUE ? -1 : change[coinsValues
                .length][amount];
    }

    @Test
    public void test1() {
        int[] values = {1, 3, 5};
        MinExchange minExchange = new MinExchange ( );
        int count = minExchange.minimumChange (values, 11);
        Assert.assertTrue (count == 3);
    }

    @Test
    public void test2() {
        int[] values = {2, 3, 5};
        MinExchange minExchange = new MinExchange ( );
        int count = minExchange.minimumChange (values, 11);
        Assert.assertTrue (count == 3);
    }

    @Test
    public void test3() {
        int[] values = {2};
        MinExchange minExchange = new MinExchange ( );
        int count = minExchange.minimumChange (values, 3);
        Assert.assertTrue(count == -1);
    }

    @Test
    public void test4() {
        int[] values = {1, 2147483647};
        MinExchange minExchange = new MinExchange ( );
        int count = minExchange.minimumChange (values, 2);
        Assert.assertTrue (count == 2);
    }
}
