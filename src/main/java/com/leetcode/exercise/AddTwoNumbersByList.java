package com.leetcode.exercise;

/**
 * Created by liqingsong on 13/06/2017.
 */
public class AddTwoNumbersByList {
    /**
     * @param l1: the first list
     * @param l2: the second list
     * @return: the sum list of l1 and l2
     */
    public ListNode addLists(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) return null;
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode tmp1 = l1;
        ListNode tmp2 = l2;


        int rootVal = tmp1.val + tmp2.val;
        boolean over = false;
        ListNode root;
        if (rootVal >= 10) {
            over = true;
            root = new ListNode (l1.val + l2.val - 10);
        } else {
            root = new ListNode (l1.val + l2.val);
        }
        ListNode child = null;
        int i = 0;
        ListNode added;
        ListNode tmpParent = root;

        tmp1 = tmp1.next;
        tmp2 = tmp2.next;
        while (tmp1 != null || tmp2 != null) {
            if (tmp1 == null) {
                added = tmp2;
                if(over){
                    added.val += 1;
                }
                tmpParent.next = added;
                over = false;
                break;
            }
            if (tmp2 == null) {
                added = tmp1;
                if(over){
                    added.val += 1;
                }
                tmpParent.next = added;
                over = false;
                break;
            }
            i = tmp1.val + tmp2.val;
            if (over) {
                i += 1;
            }
            if (i >= 10) {
                over = true;
                i -= 10;
            } else {
                over = false;
            }
            added = new ListNode (i);

            tmpParent.next = added;
            tmpParent = tmpParent.next;
            tmp1 = tmp1.next;
            tmp2 = tmp2.next;
        }
        if(over){
            ListNode highest = new ListNode (1);
            tmpParent.next = highest;
        }

        ListNode tmp = root;
        StringBuilder sbuf = new StringBuilder ( );
        while (tmp != null) {
            sbuf.insert (0, tmp.val);
            tmp = tmp.next;
        }
        System.out.println (sbuf.toString ( ));
        return root;
    }

    public static void main(String... args){
        ListNode one = new ListNode (7);
        ListNode ten = new ListNode (1);
        ListNode hundred = new ListNode (6);
        ten.next = hundred;
        one.next = ten;

//        ListNode one1 = new ListNode (5);
//        ListNode ten1 = new ListNode (9);
//        ListNode hundred1 = new ListNode (2);
//        ten1.next = hundred1;
//        one1.next = ten1;

        ListNode one1 = new ListNode (5);
        ListNode ten1 = new ListNode (9);
        one1.next = ten1;

        AddTwoNumbersByList addTwoNumbersByList = new AddTwoNumbersByList();
        addTwoNumbersByList.addLists (one, one1);
    }
}
