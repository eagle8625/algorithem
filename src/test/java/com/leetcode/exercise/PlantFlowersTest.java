package com.leetcode.exercise;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by liqingsong on 05/06/2017.
 */
public class PlantFlowersTest {

    @Test
    public void case1Test(){
        int[] inputPlots = {1,0,0,0,1};
        Assert.assertTrue ("Not permitted",PlantFlowers.available (inputPlots, 1) == true);
    }

    @Test
    public void case2Test(){
        int[] inputPlots = {1,0,0,0,1};
        Assert.assertTrue ("Not permitted",PlantFlowers.available (inputPlots, 2) == false);
    }

    @Test
    public void case3Test(){
        int[] inputPlots = {1,0,0,0,1,0,0};
        Assert.assertTrue ("Not permitted",PlantFlowers.available (inputPlots, 2) == true);
    }

    @Test
    public void case11Test(){
        int[] inputPlots = {1,0,0,0,1};
        Assert.assertTrue ("Not permitted",PlantFlowers.flowerPlace (inputPlots, 1) == true);
    }

    @Test
    public void case14Test(){
        int[] inputPlots = {1,0,0,0,1,0,0};
        Assert.assertTrue ("Not permitted",PlantFlowers.flowerPlace (inputPlots, 2) == true);
    }
}
