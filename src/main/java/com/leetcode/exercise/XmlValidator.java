package com.leetcode.exercise;

import java.util.Stack;

/**
 * Created by liqingsong on 06/06/2017.
 */
public class XmlValidator {
    public static boolean valid(String code) {
        if (code == null || code.length ( ) == 0) {
            return false;
        }
        int j = 0;
        Stack<String> tagStack = new Stack<> ( );
        for (int i = 0; i < code.length ( ) - 1; ) {
            if (code.charAt (i) == '<') {
                i++;
                if (code.charAt (i) == '/') {//end tag
                    i++;
                    j = 0;
                    while (code.charAt (i) >= 'A' && code.charAt (i) <= 'Z' && i < code.length (

                    ) - 1) {
                        j++;
                        i++;
                    }
                    if (j > 0 && code.charAt (i) == '>') {

                        if (!tagStack.empty ( ) && code.substring (i - j, i).equals (tagStack.pop
                                ( ))) {
                            i++;
                            if (tagStack.empty ( ) && !(i == code.length ( ))) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }

                } else if (code.charAt (i) == '!') {//cdata
                    if (i + 8 >= code.length ( ) - 1 || tagStack.empty ( )) return false;
                    if (code.substring (i + 1, i + 8).equalsIgnoreCase ("[CDATA[")) {
                        i = i + 8;
                        while (!code.substring (i, i + 3).equals ("]]>") && i + 3 <
                                code.length ( ) - 1) {
                            i++;
                        }
                        if (!code.substring (i, i + 3).equals ("]]>")) {
                            return false;
                        }
                        i += 3;
                        if (i >= code.length ( ) - 1) return false;
                    } else {
                        return false;
                    }
                } else if (code.charAt (i) >= 'A' && code.charAt (i) <= 'Z') {//start tag
                    j = 0;
                    while (code.charAt (i) >= 'A' && code.charAt (i) <= 'Z' && i < code.length (

                    ) - 1) {
                        i++;
                        j++;
                        if (j > 9) return false;
                    }
                    if (code.charAt (i) != '>') {//Invalid tag
                        return false;
                    }

                    System.out.println ("tag: " + code.substring (i - j, i));
                    tagStack.push (code.substring (i - j, i));
                    i++;
                } else {
                    return false;
                }
            } else {
                if (tagStack.empty ( )) return false;
                while (code.charAt (i) != '<' && i < code.length ( ) - 1) {
                    i++;
                }
                if (i == code.length ( ) - 1) {
                    return false;
                }
            }
        }
        return tagStack.empty ( );
    }
}
