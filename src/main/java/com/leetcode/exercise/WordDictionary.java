package com.leetcode.exercise;

/**
 * Created by liqingsong on 16/06/2017.
 */
public class WordDictionary {
    class TrieNode {
        public TrieNode[] children = new TrieNode[26];
        public String item = ""; //If not empty, the item is included.
    }

    private TrieNode root = new TrieNode ( );


    public void addWord(String word) {
        if (word == null || word.trim ( ).equals ("")) return;
        TrieNode parent = root;

        for (char c : word.toCharArray ( )) {
            if (parent.children[c - 'a'] == null) {
                TrieNode child = new TrieNode ( );
                parent.children[c - 'a'] = child;
            }
            parent = parent.children[c - 'a'];
        }
        parent.item = word;
    }

    public boolean search(String word){
        if(word == null ) return false;
        return match (word.toCharArray (), 0, root);
    }

    private boolean match(char[] chs, int start, TrieNode node) {
        if (start == chs.length) return !node.item.equals ("");
        if (chs[start] != '.') {
            return node.children[chs[start] - 'a'] != null && match (chs, start + 1, node
                    .children[chs[start] - 'a']);
        } else {
            for (int i = 0; i < node.children.length; i++) {
                if (node.children[i] != null) {
                    if (match (chs, start + 1, node.children[i])) {
                        return true;
                    }
                }
            }
            return false;
        }
    }


}


