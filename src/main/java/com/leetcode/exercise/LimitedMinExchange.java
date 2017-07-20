package com.leetcode.exercise;

/**
 * Created by liqingsong on 14/06/2017.
 */
public class LimitedMinExchange {
    public int limitedExchange(int[] coinsValue, int[] coinsNumber, int money) {

        int[] minExchange = new int[money + 1];
        minExchange[0] = 0;
        for (int i = 1; i <= money; i++) {
            int mininum = i;
            for (int j = 0; j < coinsValue.length; j++) {
                if (mininum > coinsValue[j]) {
                    if (coinsNumber[j] > 0) {//Still have coinsValue[j] coin.
                        int tmp = minExchange[i - coinsValue[j]] + 1;
                        if (mininum > tmp) {
                            mininum = tmp;
                        }
                    } else {
                        int recursiveMoney = i;
                        int[] recursiveCoinsValue = new int[j + 1];
                        System.arraycopy (coinsValue, 0, recursiveCoinsValue, 0, j + 1);
                        int[] recursiveCoinsNumber = new int[j + 1];
                        System.arraycopy (coinsNumber, 0, recursiveCoinsNumber, 0, j + 1);
                        mininum = limitedExchange (recursiveCoinsValue, recursiveCoinsNumber, i);
                    }
                }
            }
            minExchange[i] = mininum;
        }
        return minExchange[money];
    }
}
