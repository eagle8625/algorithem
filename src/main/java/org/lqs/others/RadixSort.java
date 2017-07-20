package org.lqs.others;

import org.junit.Assert;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by liqingsong on 27/06/2017.
 */
public class RadixSort {
    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        Queue<Integer>[] queues = new Queue[10];
        for (int i = 0; i < 10; i++) {
            queues[i] = new ConcurrentLinkedQueue<> ( );
        }

        int max = 0;
        for (int i : arr) {
            max = (max > i ? max : i);
        }

        String maxStr = Integer.toString (max);
        for (int i = 0; i < maxStr.length ( ); i++) {
            for (int j : arr) {
                int m = posNumber (j, i);
                switch (m) {
                    case 0:
                        queues[0].add (j);
                        break;
                    case 1:
                        queues[1].add (j);
                        break;
                    case 2:
                        queues[2].add (j);
                        break;
                    case 3:
                        queues[3].add (j);
                        break;
                    case 4:
                        queues[4].add (j);
                        break;
                    case 5:
                        queues[5].add (j);
                        break;
                    case 6:
                        queues[6].add (j);
                        break;
                    case 7:
                        queues[7].add (j);
                        break;
                    case 8:
                        queues[8].add (j);
                        break;
                    case 9:
                        queues[9].add (j);
                        break;
                    default:
                        break;
                }
            }
            for(int k = 0, p = 0; k < 10; k++){
                Queue<Integer> tmpQueue = queues[k];
                while (!tmpQueue.isEmpty ()){
                    arr[p++] = tmpQueue.poll ();
                }
            }
        }
    }

    /**
     * get the special postion number, e.g. if n is equal to 1, return 3 for 123, n is 1, return 2.
     *
     * @param i
     * @return
     */
    private int posNumber(int i, int n) {
        String str = Integer.toString (i);
        if(n >= str.length ()) return 0;
        return Integer.parseInt (str.substring (str.length () - n - 1, str.length () - n));
    }

    @Test
    public void test1(){
       int[] arr = {170, 45, 75, 90, 802, 2, 24, 66};
        RadixSort radixSort = new RadixSort ();
        radixSort.sort (arr);
        Assert.assertTrue ("failed", arr[0] == 2);
    }
}
