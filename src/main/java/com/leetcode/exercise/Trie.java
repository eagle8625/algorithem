package com.leetcode.exercise;

/**
 * Created by liqingsong on 16/06/2017.
 */
public class Trie {

    private TrieNode root;

    /**
     * Initialize your data structure here.
     */
    public Trie() {
        root = new TrieNode ( );
        root.children = null;
        root.value = null;
        root.childrenNum = 0;
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
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
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
        TrieNode parent = root;
        for (int i = 0; i < word.length ( ); i++) {
            TrieNode node = findChild (parent, word.charAt (i));
            if (node == null) return false;
            parent = node;
        }
        if (parent.flag == true) return true;
        else return false;
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        TrieNode parent = root;
        for (int i = 0; i < prefix.length ( ); i++) {
            TrieNode node = findChild (parent, prefix.charAt (i));
            if (node == null) return false;
            parent = node;
        }
       return true;
    }

    /**
     * There are some bugs which didn't understand
     * @param prefix
     * @param parent
     * @return
     */
    private boolean startsWith(String prefix, TrieNode parent){
        if(parent == null) return false;
        for (int i = 0; i < prefix.length ( ); i++) {
            TrieNode node = findChild (parent, prefix.charAt (i));
            if (node == null) {
                TrieNode[] children = parent.children;
                if(children != null){
                    System.out.println ("children.length:" + children.length );
                    System.out.println ("parent.childrenNum:" + parent.childrenNum );

                    for(int j = 0; i < parent.childrenNum; j++){
                        System.out.println ("j = " + j);
                        TrieNode child  = children[j];
                        boolean matching = startsWith (prefix, child);
                        if(matching) return true;
                    }
                }
                return false;
            }
            parent = node;
        }
        return true;
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
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
