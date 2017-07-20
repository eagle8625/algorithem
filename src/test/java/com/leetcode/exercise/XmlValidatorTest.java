package com.leetcode.exercise;

import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

/**
 * Created by liqingsong on 06/06/2017.
 */
public class XmlValidatorTest {
    @Test
    public void validTest1(){
        boolean res = XmlValidator.valid ("<DIV>This is the first line <![CDATA[<div>]]></DIV>");
        Assert.that (res == true, "valid failed" );
    }

    @Test
    public void validTest2(){
        boolean res = XmlValidator.valid ("<DIV>>>  ![cdata[]] <![CDATA[<div>]>]]>]]>>]</DIV>");
        Assert.that (res == true, "valid failed" );
    }

    @Test
    public void validTest3(){
        boolean res = XmlValidator.valid ("<A>  <B> </A>   </B>");
        Assert.that (res == false, "valid failed" );
    }

    @Test
    public void validTest4(){
        boolean res = XmlValidator.valid ("<DIV>  div tag is not closed  <DIV>");
        Assert.that (res == false, "valid failed" );
    }

    @Test
    public void validTest5(){
        boolean res = XmlValidator.valid ("<DIV>  unmatched <  </DIV>");
        Assert.that (res == false, "valid failed" );
    }

    @Test
    public void validTest6(){
        boolean res = XmlValidator.valid ("<DIV> closed tags with invalid tag name  <b>123</b> </DIV>");
        Assert.that (res == false, "valid failed" );
    }

    @Test
    public void validTest7(){
        boolean res = XmlValidator.valid ("<DIV> unmatched tags with invalid tag name  </1234567890> and <CDATA[[]]>  </DIV>");
        Assert.that (res == false, "valid failed" );
    }

    @Test
    public void validTest8(){
        boolean res = XmlValidator.valid ("<DIV>  unmatched start tag <B>  and unmatched end tag </C>  </DIV>");
        Assert.that (res == false, "valid failed" );
    }

    @Test
    public void validTest9(){
        boolean res = XmlValidator.valid ("<A><!A></A>");
        Assert.that (res == false, "valid failed" );
    }

    @Test
    public void validTest10(){
        boolean res = XmlValidator.valid ("<A></A><B></B>");
        Assert.that (res == false, "valid failed" );
    }

    @Test
    public void validTest11(){
        boolean res = XmlValidator.valid ("<![CDATA[ABC]]><TAG>sometext</TAG>");
        Assert.that (res == false, "valid failed" );
    }

    @Test
    public void validTest12(){
        boolean res = XmlValidator.valid ("!!!<A>123</A>");
        Assert.that (res == false, "valid failed" );
    }


}
