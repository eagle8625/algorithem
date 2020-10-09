package com.leetcode.exercise;


import org.junit.Assert;
import org.junit.Test;

/**
 * Created by liqingsong on 14/06/2017.
 */
public class FindWin {
    /**
     * @param coinTypes
     * @param total
     * @return 0: no winner, 1: the first failed; 2: the second failed
     */
    public int getWinner(int[] coinTypes, int total) {
        if (total == 0) return 0;
        if (total == 1) return 1;
        if (total == 2) return 2;
        int[] totalLoser = new int[total + 1];

        totalLoser[0] = 0;
        totalLoser[1] = 1;
        totalLoser[2] = 2;
        totalLoser[3] = 2;
        totalLoser[4] = 1;
        for (int n = 5; n <= total; n++) {
            int firstOneFailedTimes = 0;
            for (int i = 0; i < coinTypes.length; i++) {
                if (n - coinTypes[i] > 0) {
                    int person = totalLoser[n - coinTypes[i]];
                    if (person == 2) {//Once the first wins, terminate the loop.
                        totalLoser[n] = 2;
                        break;
                    } else {
                        firstOneFailedTimes++;
                    }
                }
            }
            if(firstOneFailedTimes == coinTypes.length){
                totalLoser[n] = 1;
            }
        }
        return totalLoser[total];
    }

    @Test
    public void test1(){
        int[] coinTypes = {1, 2, 4};
        FindWin findWin = new FindWin ();
        int failed = findWin.getWinner (coinTypes, 4);
        Assert.assertTrue (failed == 1);
    }


    @Test
    public void test2(){
        int[] coinTypes = {1, 2, 4};
        FindWin findWin = new FindWin ();
        int failed = findWin.getWinner (coinTypes, 5);
        Assert.assertTrue("Judge failed",failed == 2);
    }

    @Test
    public void test3(){
        int[] coinTypes = {1, 2, 4};
        FindWin findWin = new FindWin ();
        int failed = findWin.getWinner (coinTypes, 6);
        Assert.assertTrue ("Judge failed",failed == 2);
    }
}
