package com.leetcode.exercise;

/**
 * Created by liqingsong on 05/06/2017.
 */
public class PlantFlowers {
    /**
     * liqingsong
     * @param flowerbed
     * @param n
     * @return
     */
    public static boolean available(int[] flowerbed, int n) {
        int emptyPlotNum = 0;
        int prePlot = 0;
        int sufPlot = 0;
        int[] copyArr = new int[flowerbed.length];
        System.arraycopy (flowerbed, 0, copyArr, 0, flowerbed.length);

        for (int i = 0; i < flowerbed.length; i++) {
            if (flowerbed[i] == 1) continue;
            if (i > 0) {
                prePlot = flowerbed[i - 1];
            }
            if (i == flowerbed.length - 1) {
                sufPlot = 0;
            } else {
                sufPlot = flowerbed[i + 1];
            }

            if (prePlot == 0 && sufPlot == 0) {
                emptyPlotNum++;
                flowerbed[i] = 1;
            }
        }
        return emptyPlotNum >= n;
    }

    /**
     * leetcode
     * @param flowerbed
     * @param n
     * @return
     */
    public static boolean flowerPlace(int[] flowerbed, int n) {
        int canPlace = 0;
        for (int i = 0; i < flowerbed.length; i++) {
            if (flowerbed[i] == 0 && (i == 0 || flowerbed[i - 1] == 0) && (i == flowerbed.length
                    - 1 || flowerbed[i + 1] == 0)) {
                canPlace++;
            }
            if (canPlace >= n) return true;
        }
        return false;
    }
}
