package com.leetcode.exercise;

import java.util.*;

/**
 * Created by liqingsong on 20/07/2017.
 * Another 2 approaches, one is using one level indexing, another is using Trie.
 */
public class AutocompleteSystem {
    private HashMap<String, Integer> map = new HashMap<> ( );
    private char[] searchStr = new char[100];
    private int elementNum;

    public HashMap<String, Integer> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Integer> map) {
        this.map = map;
    }

    public AutocompleteSystem(String[] sentences, int[] times) {
        if (sentences == null || sentences.length == 0 || times == null || times.length == 0) {
            return;
        } else {
            int i = 0;
            for (String sentence : sentences) {
                map.put (sentence, times[i++]);
            }
        }
    }

    public List<String> input(char c) {
        if (c == '#') {
            String nstr = new String (searchStr, 0, elementNum);
            map.put (nstr, map.getOrDefault (nstr, 0) + 1);
            for(int i = 0 ; i < elementNum; i++){
                searchStr[i] = '\0';
            }
            elementNum = 0;
            return new LinkedList<> ();
        }
        searchStr[elementNum++] = c;
//        return topThree (new String (searchStr, 0, elementNum));
        return collectionCompare (new String (searchStr, 0, elementNum));
    }

    class Node{
        public String sentence;
        public int times;
        public Node(String sentence, int times){
            this.sentence = sentence;
            this.times = times;
        }
    }

    private List<String> collectionCompare(String source){
        List<Node> list = new ArrayList<> ();
        for(Map.Entry<String, Integer> entry: map.entrySet ()){
            if(entry.getKey ().indexOf (source) == 0){
                Node node = new Node(entry.getKey (), entry.getValue ());
                list.add (node);
            }
        }
        Collections.sort(list, (a,b)->a.times == b.times ? a.sentence.compareTo (b.sentence) : b
                .times - a.times);
        List<String> res = new ArrayList<> ();
        for(int i = 0; i < Math.min (3, list.size ()); i++){
            res.add (list.get (i).sentence);
        }
        return res;
    }

    /**
     * Use common compare method
     * @param source
     * @return
     */
    private List<String> topThree(String source) {
        Map<String, Integer> matched = new HashMap ( );
        for (Map.Entry<String, Integer> entry : map.entrySet ( )) {
            if ((entry.getKey ( )).startsWith (source)) {
               matched.put (entry.getKey (), entry.getValue ());
            }
        }
        List<String> list = new LinkedList<> ( );
        for (int i = 0; i < 3; i++) {
            if (matched.isEmpty ()) {
                break;
            }
            Map.Entry<String, Integer> maxValue = null;

            for (Map.Entry<String, Integer> entry : matched.entrySet ( )) {
                if (maxValue == null) {
                    maxValue = entry;
                    continue;
                } else {
                    if (entry.getValue ( ) > maxValue.getValue ( )) {
                        maxValue = entry;
                    } else if (entry.getValue ( ).equals (maxValue.getValue ( ))) {
                        if (entry.getKey ( ).charAt (source.length ( )) < maxValue.getKey ( ).charAt
                                (source.length ( ))) {
                            maxValue = entry;
                        }
                    }
                }
            }
            if (maxValue != null) {
                list.add (maxValue.getKey ( ));
                matched.remove (maxValue.getKey ( ));
            }
        }
        return list;
    }
}
