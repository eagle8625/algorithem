package com.leetcode.exercise;

import java.util.*;

/**
 * Created by liqingsong on 17/06/2017.
 */
public class Solution {

    public class UndirectedGraphNode {
        int label;
        List<UndirectedGraphNode> neighbors;

        UndirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<UndirectedGraphNode> ( );
        }
    }

    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        return dnfClone (node, new HashMap<Integer, UndirectedGraphNode> ( ));
    }

    private UndirectedGraphNode dnfClone(UndirectedGraphNode node, Map<Integer,
            UndirectedGraphNode> map) {
        if (node == null) return null;
        if (map.containsKey (node.label)) {
            return map.get (node.label);
        }
        UndirectedGraphNode nodeNew = new UndirectedGraphNode (node.label);
        map.put (node.label, nodeNew);
        for (UndirectedGraphNode neighbour : node.neighbors) {
            nodeNew.neighbors.add (dnfClone (neighbour, map));
        }
        return nodeNew;
    }

    /**
     * https://leetcode.com/problems/evaluate-division/#/description
     */
    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        double[][] graph = constructGraph (equations, values);
        double[] results = new double[queries.length];
        for (int i = 0; i < results.length; i++) {
            double result = dnf (graph, queries[i][0].charAt (0), queries[i][1].charAt (0));
            if (result == -1.0) {
                result = dnf (graph, queries[i][1].charAt (0), queries[i][0].charAt (0));
                result = 1 / result;
            }
            results[i] = result;
        }
        return results;
    }

    /**
     * There is infinite loop, so need define a set to save nodes visited.
     *
     * @param graph
     * @param left
     * @param right
     * @return
     */
    private double dnf(double[][] graph, char left, char right) {
        if (left == right) {
            if (graph[left - 'a'][right - 'a'] == -1.0) return -1.0;
            else return 1.0;
        }

        double internal = 1.0;
        for (int j = 0; j < 26; ) {
            if (j == (right - 'a')) {
                if (graph[left - 'a'][j] != -1.0) {
                    return graph[left - 'a'][j];
                } else {

                }
            }
            internal = graph[left - 'a'][j];

            j++;
            char leftN = (char) (j + 'a');
            internal = internal * dnf (graph, leftN, right);

        }
        if (internal < 0) return -1.0;
        else return internal;
    }

    private double[][] constructGraph(String[][] equations, double[] values) {
        if (equations == null || values == null || equations.length != values.length) {
            return null;
        }
        double[][] graph = new double[26][26];
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                graph[i][j] = -1.0;
            }
        }
        char left;
        char right;
        for (int i = 0; i < equations.length; i++) {
            left = equations[i][0].charAt (0);
            right = equations[i][1].charAt (0);
            graph[left - 'a'][right - 'a'] = values[i];
        }
        return graph;
    }

    public double[] calcEquation1(String[][] equations, double[] values, String[][] queries) {
        HashMap<String, ArrayList<String>> pairs = new HashMap<String, ArrayList<String>> ( );
        HashMap<String, ArrayList<Double>> valuesPair = new HashMap<String, ArrayList<Double>> ( );
        for (int i = 0; i < equations.length; i++) {
            String[] equation = equations[i];
            if (!pairs.containsKey (equation[0])) {
                pairs.put (equation[0], new ArrayList<String> ( ));
                valuesPair.put (equation[0], new ArrayList<Double> ( ));
            }
            if (!pairs.containsKey (equation[1])) {
                pairs.put (equation[1], new ArrayList<String> ( ));
                valuesPair.put (equation[1], new ArrayList<Double> ( ));
            }
            pairs.get (equation[0]).add (equation[1]);
            pairs.get (equation[1]).add (equation[0]);
            valuesPair.get (equation[0]).add (values[i]);
            valuesPair.get (equation[1]).add (1 / values[i]);
        }

        double[] result = new double[queries.length];
        for (int i = 0; i < queries.length; i++) {
            String[] query = queries[i];
            result[i] = dfs (query[0], query[1], pairs, valuesPair, new HashSet<String> ( ), 1.0);
            if (result[i] == 0.0) result[i] = -1.0;
        }
        return result;
    }

    private double dfs(String start, String end, HashMap<String, ArrayList<String>> pairs,
                       HashMap<String, ArrayList<Double>> values, HashSet<String> set, double
                               value) {
        if (set.contains (start)) return 0.0;
        if (!pairs.containsKey (start)) return 0.0;
        if (start.equals (end)) return value;
        set.add (start);

        ArrayList<String> strList = pairs.get (start);
        ArrayList<Double> valueList = values.get (start);
        double tmp = 0.0;
        for (int i = 0; i < strList.size ( ); i++) {
            tmp = dfs (strList.get (i), end, pairs, values, set, value * valueList.get (i));
            if (tmp != 0.0) {
                break;
            }
        }
        set.remove (start);
        return tmp;
    }

    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        if (strs.length == 1) return strs[0];

        String lcp = "";
        String tmp = strs[0];

        for (int i = 0; i < strs.length; i++) {
            lcp = lcp (strs[i], tmp);
            if (lcp.equals ("")) break;
            tmp = lcp;
        }
        return lcp;
    }

    private String lcp(String str1, String str2) {
        if (str1 == null || str2 == null) return "";
        int min = Math.min (str1.length ( ), str2.length ( ));
        StringBuilder buf = new StringBuilder ( );
        for (int i = 0; i < min; i++) {
            if (str1.charAt (i) == str2.charAt (i)) {
                buf.append (str1.charAt (i));
            } else {
                break;
            }
        }
        return buf.toString ( );
    }

    public boolean isMatch(String s, String p) {
        int n1 = s.length ( ), n2 = p.length ( );
        boolean[][] dp = new boolean[n1 + 1][n2 + 1];

        dp[0][0] = true;

        for (int j = 1; j <= n2; ++j) {
            if (p.charAt (j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        for (int i = 1; i <= n1; ++i) {
            for (int j = 1; j <= n2; ++j) {
                if (p.charAt (j - 1) == '*') {
                    dp[i][j] = dp[i][j - 2] || (dp[i - 1][j] && (s.charAt (i - 1) == p.charAt (j
                            - 2) || p.charAt (j - 2) == '.'));
                } else {
                    dp[i][j] = dp[i - 1][j - 1] && (s.charAt (i - 1) == p.charAt (j - 1) || p
                            .charAt (j - 1) == '.');
                }
            }
        }
        return dp[n1][n2];
    }

    /**
     * https://leetcode.com/problems/regular-expression-matching/#/description
     * This is recursive version
     *
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch1(String s, String p) {
        if (p.isEmpty ( )) return s.isEmpty ( );
        if (p.length ( ) > 1 && '*' == p.charAt (1)) {
            return (isMatch1 (s, p.substring (2))) || !s.isEmpty ( ) && (s.charAt (0) == p.charAt
                    (0) || p.charAt (0) == '.') && isMatch1 (s.substring (1), p);
        } else {
            return (!s.isEmpty ( ) && (s.charAt (0) == p.charAt (0) || '.' == p.charAt (0)) &&
                    isMatch1 (s.substring (1), p.substring (1)));
        }

    }

    public int lcsLength(String a, String b) {
        if (a == null || b == null) return 0;
        int[][] dp = new int[1 + a.length ( )][1 + b.length ( )];
        for (int i = 0; i <= a.length ( ); i++) {
            for (int j = 0; j <= b.length ( ); j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                } else if (a.charAt (i - 1) == b.charAt (j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max (dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        System.out.println (backtrack (dp, a, b, a.length ( ), b.length ( )));

        return dp[a.length ( )][b.length ( )];
    }

    private String backtrack(int[][] dp, String a, String b, int i, int j) {
        if (i == 0 || j == 0) return "";
        else if (a.charAt (i - 1) == b.charAt (j - 1)) {
            return backtrack (dp, a, b, i - 1, j - 1) + a.charAt (i - 1);
        } else if (dp[i - 1][j] > dp[i][j - 1]) {
            return backtrack (dp, a, b, i - 1, j);
        } else {
            return backtrack (dp, a, b, i, j - 1);
        }
    }

    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        int len = 0;

        for(int x : nums) {
            int i = Arrays.binarySearch(dp, 0, len, x);
            if(i < 0) i = -(i + 1);
            dp[i] = x;
            if(i == len) len++;
        }

        return len;
    }

}
