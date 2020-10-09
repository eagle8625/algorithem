package org.lqs.others;

import com.leetcode.common.Node;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by liqingsong on 14/07/2017.
 */
public class Bns {

    private Node[] arr = new Node[1000];
    private static int count = 0;

    public void bf(Node root) {
        dns (root, 0);
        int j = 0;
        double c = 0;
        for (int i = 0; i < count; ) {
            c = Math.pow (2, j);
            StringBuilder stringBuilder = new StringBuilder ( );
            for (int k = i; k < i + c; k++) {
                if (arr[k] != null) {
                    stringBuilder.append (arr[k].val).append (" ");
                }
            }
            i += c;
            j += 1;
            System.out.println (stringBuilder.toString ( ));
        }
    }

    private void dns(Node node, int idx) {
        arr[idx] = node;
        count += 1;
        if (node != null) {
            if (node.left != null) {
                dns (node.left, 2 * idx + 1);
            }
            if (node.right != null) {
                dns (node.right, 2 * idx + 2);
            }
        }
    }


    public void bnfUseQueue(Node node){
        Queue<Node> nodeQueue = new LinkedBlockingQueue<>();
        Queue<Node> innerQueue = new LinkedBlockingQueue<> ();
        if(node == null) {
            return;
        }

        nodeQueue.add(node);
        while(!nodeQueue.isEmpty ()){
            while(!nodeQueue.isEmpty ()){
                Node tmpNode = nodeQueue.poll ();
                innerQueue.add (tmpNode);
            }

            StringBuilder stringBuilder = new StringBuilder ();
            while(!innerQueue.isEmpty ()){
                Node tmpNode = innerQueue.poll ();
                stringBuilder.append (tmpNode.val).append (" ");
                if(tmpNode.left != null){
                    nodeQueue.add (tmpNode.left);
                }
                if(tmpNode.right != null){
                    nodeQueue.add (tmpNode.right);
                }
            }
            System.out.println (stringBuilder.toString ());
        }
    }

    public static void main(String[] args) {
        Bns bns = new Bns ( );
        Node root = new Node ( );
        root.val = 1;
        Node left = new Node ( );
        left.val = 2;
        Node right = new Node ( );
        right.val = 3;
        root.left = left;
        root.right = right;

        Node rightLeft = new Node ( );
        rightLeft.val = 4;
        right.left = rightLeft;

//        bns.bf (root);
        bns.bnfUseQueue (root);
    }
}
