package com.leetcode.exercise;

/**
 * Created by liqingsong on 26/07/2017.
 */
public class AddTwoNumbersSolution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }
        ListNode tmpL1 = l1;
        ListNode tmpL2 = l2;

        ListNode sumNode = null;

        //restore the lowest position
        ListNode tailNode = null;
        ListNode headNode = null;
        int sum;
        while (tmpL1 != null || tmpL2 != null) {
            sum = 0;
            if (tmpL1 != null) {
                sum += tmpL1.val;
                tmpL1 = tmpL1.next;
            }
            if (tmpL2 != null) {
                sum += tmpL2.val;
                tmpL2 = tmpL2.next;
            }
            headNode = new ListNode (sum);
            if (sumNode != null) {
                sumNode.next = headNode;
                sumNode = headNode;
            } else {
                sumNode = headNode;
                tailNode = headNode;
            }
        }

        sumNode = tailNode;
        boolean overTen = false;
        while (sumNode != null) {
            if (overTen == true) {
                sumNode.val += 1;
            }

            if (sumNode.val >= 10) {
                overTen = true;
                sumNode.val -= 10;
            } else {
                overTen = false;
            }
            sumNode = sumNode.next;
        }

        if (overTen) {
            ListNode node = new ListNode (1);
            headNode.next = node;
        }

        return tailNode;
    }
}

