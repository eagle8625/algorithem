package com.leetcode.exercise;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by liqingsong on 06/06/2017.
 */
public class XmlValidatorTest {
    @Test
    public void validTest1(){
        boolean res = XmlValidator.valid ("<DIV>This is the first line <![CDATA[<div>]]></DIV>");
        assertTrue("valid failed", res);
    }

    @Test
    public void validTest2(){
        boolean res = XmlValidator.valid ("<DIV>>>  ![cdata[]] <![CDATA[<div>]>]]>]]>>]</DIV>");
        assertTrue("valid failed", res);
    }

    @Test
    public void validTest3(){
        boolean res = XmlValidator.valid ("<A>  <B> </A>   </B>");
        assertFalse("valid failed", res);
    }

    @Test
    public void validTest4(){
        boolean res = XmlValidator.valid ("<DIV>  div tag is not closed  <DIV>");
        assertFalse("valid failed", res);
    }

    @Test
    public void validTest5(){
        boolean res = XmlValidator.valid ("<DIV>  unmatched <  </DIV>");
        assertFalse("valid failed", res);
    }

    @Test
    public void validTest6(){
        boolean res = XmlValidator.valid ("<DIV> closed tags with invalid tag name  <b>123</b> </DIV>");
        assertFalse("valid failed", res);
    }

    @Test
    public void validTest7(){
        boolean res = XmlValidator.valid ("<DIV> unmatched tags with invalid tag name  </1234567890> and <CDATA[[]]>  </DIV>");
        assertFalse("valid failed", res);
    }

    @Test
    public void validTest8(){
        boolean res = XmlValidator.valid ("<DIV>  unmatched start tag <B>  and unmatched end tag </C>  </DIV>");
        assertFalse("valid failed", res);
    }

    @Test
    public void validTest9(){
        boolean res = XmlValidator.valid ("<A><!A></A>");
        assertFalse("valid failed", res);
    }

    @Test
    public void validTest10(){
        boolean res = XmlValidator.valid ("<A></A><B></B>");
        assertFalse("valid failed", res);
    }

    @Test
    public void validTest11(){
        boolean res = XmlValidator.valid ("<![CDATA[ABC]]><TAG>sometext</TAG>");
        assertFalse("valid failed", res);
    }

    @Test
    public void validTest12(){
        boolean res = XmlValidator.valid ("!!!<A>123</A>");
        assertFalse("valid failed", res);
    }


}
