package com.leetcode.exercise;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liqingsong on 16/06/2017.
 */
public class SolutionTest {
    private HashMap<Character, Integer> charCountMap;

    public String frequencySort(String s) {
        charCountMap = new HashMap ( );

        for (char c : s.toCharArray ( )) {
            if (charCountMap.containsKey (c)) {
                charCountMap.put (c, charCountMap.get (c) + 1);
            } else {
                charCountMap.put (c, 1);
            }
        }
        //Must plus 1 as entry's value may be equal to the length.
        List<Character>[] bucket = new List[s.length ( ) + 1];
        for (Map.Entry<Character, Integer> entry : charCountMap.entrySet ( )) {
            if (bucket[entry.getValue ( )] == null) {
                bucket[entry.getValue ( )] = new ArrayList<> ( );
            }
            bucket[entry.getValue ( )].add (entry.getKey ( ));
        }

        StringBuilder buf = new StringBuilder ( );

        for (int pos = bucket.length - 1; pos >= 0; pos--) {
            if (bucket[pos] != null) {
                List<Character> list = bucket[pos];
                for (Character c : list) {
                    for (int i = 0; i < pos; i++) {
                        buf.append (c);
                    }
                }
            }
        }
        return buf.toString ( );

    }

    @Test
    public void test1(){
        SolutionTest solution= new SolutionTest ();

        String str = solution.frequencySort ("tree");
        Assert.assertTrue ("failed",str.startsWith ("ee"));
    }

    @Test
    public void test2(){
        SolutionTest solution= new SolutionTest ();

        String str = solution.frequencySort ("eeeee");
        System.out.println (str );
    }

    @Test
    public void bnfTest(){
        Solution solution = new Solution ();
        Solution.UndirectedGraphNode node1 = solution.new UndirectedGraphNode (1);
        node1.neighbors.add (node1);

        Solution.UndirectedGraphNode nodeNew = solution.cloneGraph (node1);
    }

    @Test
    public void calcEquationTest(){
        Solution solution = new Solution ();
        String[][] equations = {{"a", "b"}, {"b", "c"}};
        double[] values = {2.0, 3.0};
        String[][] queries = { {"a", "c"}, {"b", "a"}, {"a", "e"}, {"a", "a"}, {"x", "x"}};
        double[] result = solution.calcEquation (equations, values, queries);
        Assert.assertTrue ("f", result[4] == -1.0);
    }

    @Test
    public void lcpTest(){
        int i = "abc".indexOf ("abcd");
        Assert.assertTrue ("f", i == -1);

        i = "abcd".indexOf ("abc");
        Assert.assertTrue ("f", i == 0);

        i = "eabcd".indexOf ("abc");
        Assert.assertTrue ("f", i == 1);


        i = "eabcd".indexOf ("eabd");
        Assert.assertTrue ("f", i == 1);

        String[] strs = {"abc", "ab", "bcd"};
        Solution solution = new Solution ();
        String lcp = solution.longestCommonPrefix (strs);
        Assert.assertTrue ("f", lcp.equals (""));

        String[] strs1 = {"a", "a", "b"};
        lcp = solution.longestCommonPrefix (strs1);
        Assert.assertTrue ("f", lcp.equals (""));
    }

    @Test
    public void matchTest(){
        Solution solution = new Solution ();
        boolean res = solution.isMatch ("a", "a");
        Assert.assertTrue ("f", res == true);

        res = solution.isMatch ("aa", "a");
        Assert.assertTrue ("f", res == false);
        res = solution.isMatch ("aa", "aa");
        Assert.assertTrue ("f", res == true);
        res = solution.isMatch ("aaa", "aa");
        Assert.assertTrue ("f", res == false);
        res = solution.isMatch ("aaa", "a*");
        Assert.assertTrue ("f", res == true);
        res = solution.isMatch ("aa", ".*");
        Assert.assertTrue ("f", res == true);
        res = solution.isMatch ("ab", ".*");
        Assert.assertTrue ("f", res == true);
        res = solution.isMatch ("aab", "c*a*b");
        Assert.assertTrue ("f", res == true);

        res = solution.isMatch ("ab", ".*c");
        Assert.assertTrue ("f", res == false);

        res = solution.isMatch ("aaa", "aaaa");
        Assert.assertTrue ("f", res == false);

        res = solution.isMatch ("aaa", "a*a");
        Assert.assertTrue ("f", res == true);
    }

    @Test
    public void lcsLengthTest(){
        Solution solution = new Solution ();
        int n = solution.lcsLength ("abcd", "abde");
        Assert.assertTrue ("f", n == 3);
    }

    @Test
    public void lengthOfLISTest(){
        Solution solution = new Solution ();
        int[] a = {10, 9, 2, 5, 3, 7, 101, 18};
        int n = solution.lengthOfLIS (a);
        Assert.assertTrue ("f", n == 4);
    }

}
