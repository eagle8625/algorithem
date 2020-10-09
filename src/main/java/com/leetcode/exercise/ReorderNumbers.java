package com.leetcode.exercise;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by liqingsong on 13/06/2017.
 * Move all 0 to the header, following non-zero numbers.
 */
public class ReorderNumbers {
    public String convert(int in){
        String str = Integer.toString (in);

        StringBuilder stringBuilder = new StringBuilder ();
        for(int i = 0; i < str.length (); i++){
            char c = str.charAt (i);
            if(c == '0'){
                stringBuilder.insert (0, c);
            }else{
                stringBuilder.append (c);
            }
        }

        return stringBuilder.toString ();
    }

    @Test
    public void test1(){
        ReorderNumbers reorderNumbers = new ReorderNumbers ();

        String str = reorderNumbers.convert (104802035);
        Assert.assertTrue(str.equals("000148235"));
    }

    @Test
    public void test2(){
        ReorderNumbers reorderNumbers = new ReorderNumbers ();

        String str = reorderNumbers.convert (1000402035);
        Assert.assertTrue (str.equals ("0000014235"));
    }
}
