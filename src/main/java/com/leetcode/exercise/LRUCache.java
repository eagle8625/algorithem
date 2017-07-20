package com.leetcode.exercise;

import java.util.Hashtable;

/**
 * Created by liqingsong on 14/06/2017.
 */
public class LRUCache {
    private Hashtable<Integer, Item> cache;
    private Item head;
    private Item tail;

    private int capacity;
    private int real;

    public LRUCache(){}

    public LRUCache(int capacity) {
        this.real = 0;
        this.capacity = capacity;
        head = new Item ();
        head.pre = null;
        tail = new Item ();
        tail.post = null;
        head.post = tail;
        tail.pre = head;
        cache = new Hashtable<> ();
    }

    public int get(int key) {
        Item item = this.cache.get (key);
        if(item == null) return -1;
        moveToHead (item);
        return item.value;
    }

    public void put(int key, int value) {
        Item item = cache.get (key);
        if(item == null){
            item = new Item ();
            item.key = key;
            item.value = value;
            add (item);
            cache.put (key, item);
            if(real < capacity){
                real++;
            }else{
                Item oldest = popTail ();
                cache.remove (oldest.key);
            }
        }else{
            item.value = value;
            moveToHead (item);
        }


    }

    private void removeItem(Item item){
        Item pre = item.pre;
        Item post = item.post;

        pre.post = post;
        post.pre = pre;
    }

    private Item popTail(){
        Item oldest = tail.pre;
        removeItem (oldest);
        return oldest;
    }

    private void moveToHead(Item item){
        removeItem (item);
        add (item);
    }

    /**
     * always add new item following head
     * @param item
     */
    private void add(Item item){
        item.post = head.post;
        item.pre = head;

        head.post.pre = item;
        head.post = item;
    }
}

class Item {
     Item pre;
     Item post;
     int key;
     int value;
}
