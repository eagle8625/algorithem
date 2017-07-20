package com.leetcode.exercise;

/**
 * Created by liqingsong on 16/06/2017.
 */
public class WordDictionaryWithFault {
    private TrieNode root;

    /**
     * Initialize your data structure here.
     */
    public WordDictionaryWithFault() {
        root = new TrieNode ( );
        root.children = new TrieNode[26];
        root.childrenNum = 0;
        root.parent = null;
    }

    /**
     * Adds a word into the data structure.
     */
    public void addWord(String word) {
        if (word == null || word.trim ( ).equals ("")) return;
        TrieNode parent = root;
        TrieNode tmp = null;
        for (int i = 0; i < word.length ( ); i++) {
            char c = word.charAt (i);
            tmp = findChild (parent, c);
            if (tmp == null) {//Parent doesn't contain child with value c.
                tmp = addChild (parent, c);
            }
            parent = tmp;
        }
        if (tmp != null) tmp.flag = true;
    }

    /**
     * Returns if the word is in the data structure. A word could contain the dot character '.'
     * to represent any one letter.
     */
    public boolean search(String word) {
        if (word == null || word.trim ( ).equals ("")) return false;
        TrieNode[] parents = new TrieNode[26];
        parents[0] = root;
        int num = 1;
        for (int i = 0; i < word.length ( ); ) {
            if (word.charAt (i) == '.') {
                if (i == word.length ( ) - 1) {
                    for (int p = 0; p < num; p++) {
                        if (parents[0].childrenNum > 0) {
                            for(int cn = 0; cn < parents[0].childrenNum; cn++){
                                TrieNode tmp = parents[0].children[0];
                                if(tmp.flag == true){
                                    return true;
                                }
                            }
                            return false;
                        }
                    }
                    return false;
                }
                int searchsize = 0;
                for (int p = 0; p < num; p++) {
                    searchsize += parents[p].childrenNum;
                }
                TrieNode[] newParents = new TrieNode[searchsize];
                int s = 0;
                for (int p = 0; p < num; p++) {
                    if (parents[p].childrenNum > 0) {
                        System.arraycopy (parents[p].children, 0, newParents, s, parents[p]
                                .childrenNum);
                        s += parents[p].childrenNum;
                    }
                }
                num = searchsize;
                parents = newParents;
                i++;
            } else {
                int j = 0;
                TrieNode node = null;
                for (; j < num; j++) {
                    node = findChild (parents[j], word.charAt (i));
                    if (node != null) {
                        parents[0] = node;
                        num = 1;
                        break;
                    }
                }
                if (node == null) {
                    return false;
                }
                i++;
            }
        }
        if (num >= 1) {
            for (int k = 0; k < num; k++) {
                TrieNode parent = parents[k];
                if (parent.flag == true) return true;
            }
        }
        return false;
    }

    /**
     * match
     *
     * @param parent
     * @param c
     * @return
     */
    private TrieNode[] matchingGrandson(TrieNode parent, char c) {
        TrieNode[] children = parent.children;
        if (c == '.') return children;
        for (int i = 0; i < parent.childrenNum; i++) {
            TrieNode child = children[i];
            TrieNode node = findChild (child, c);
            if (node != null) {
                TrieNode[] children1 = new TrieNode[1];
                children1[0] = node;
                return children1;
            }
        }
        return null;
    }

    /**
     * Compare node'value with c, if equal return true or false
     *
     * @param node
     * @param c
     * @return
     */
    private boolean compare(TrieNode node, char c) {
        if (node == null) return false;
        return node.value.equals (c) ? true : false;
    }

    /**
     * Find the child of which value is c
     *
     * @param parent
     * @param c
     * @return
     */
    private TrieNode findChild(TrieNode parent, char c) {
        if (parent == null || parent.childrenNum == 0) {
            return null;
        }

        for (int i = 0; i < parent.childrenNum; i++) {
            TrieNode child = parent.children[i];
            if (child.value.equals (c)) return child;
        }

        return null;
    }

    private TrieNode addChild(TrieNode parent, char c) {
        TrieNode newNode = new TrieNode ( );
        newNode.parent = parent;
        newNode.value = c;
        newNode.children = null;
        newNode.childrenNum = 0;
        TrieNode[] children = parent.children;
        if (children == null) {
            children = new TrieNode[26];
        }
        int index = parent.childrenNum++;
        children[index] = newNode;
        parent.children = children;
        return newNode;
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
