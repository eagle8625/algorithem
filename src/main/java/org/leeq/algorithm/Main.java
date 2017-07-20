package org.leeq.algorithm;

import org.leeq.algorithm.impl.TopNPopularPathServiceImpl;
import org.leeq.algorithm.visitsimulator.SimulatorUtil;

import java.util.Scanner;

/**
 * Created by liqingsong on 27/05/2017.
 */
public class Main {
    public static void main(String... args) {
        int testCount = 0;

        while (true) {
            Scanner reader = new Scanner (System.in);
            if(testCount++ > 0){
                System.out.println ("\n" );
            }
            System.out.println ("Choose test data set, \n\r\t1: from the " +
                    "homework" +
                    "\n\r\t2: user's input");

            int n = reader.nextInt ( );

            if (n == 1) {
                TopNPopularPathService topNPopularPathService = new TopNPopularPathServiceImpl ( );
                String[][] visitPathes = new String[][]{
                        {"U1", "/"},
                        {"U1", "subscribers"},
                        {"U2", "/"},
                        {"U2", "subscribers"},
                        {"U1", "filter"},
                        {"U1", "export"},
                        {"U2", "filter"},
                        {"U2", "export"},
                        {"U3", "/"},
                        {"U3", "catalog"},
                        {"U3", "edit"},
                        {"U4", "/"},
                        {"U4", "invoice"},
                        {"U4", "pdf"},
                };
                topNPopularPathService.setup (visitPathes);
                String[] popularPathes = topNPopularPathService.getTopNPopularPaths (4);
                int order = 1;
                for (String path : popularPathes) {
                    System.out.println (order++ + "," + path);
                }

                topNPopularPathService.getTopNPopularPathsAmount (4).entrySet ( ).stream ( ).forEach
                        (entry -> {
                            System.out.printf ("path: %s, visit amount: %d\n", entry.getKey ( ),
                                    entry
                                    .getValue ( ));
                        });
            } else if (n == 2) {
                System.out.println ("Provide 3 numbers split by commas: path number, " +
                        "user number, " +
                        "top" +
                        "number, e.g. 100000,100,10: ");
                String nums = reader.next ( );
                String[] arr = nums.split (",");
                if (Integer.parseInt (arr[0]) <= 0 || Integer.parseInt (arr[1]) <= 0 || Integer
                        .parseInt (arr[2]) <= 0) {
                    System.err.println ("Invalid number");
                    continue;
                }
                int k = Integer.parseInt (arr[2]);

                TopNPopularPathService topNPopularPathService = new TopNPopularPathServiceImpl ( );
                topNPopularPathService.setup (SimulatorUtil.getVistiData (Integer.parseInt (arr[0]),
                        Integer.parseInt (arr[1])));

                String[] popularPathes = topNPopularPathService.getTopNPopularPaths (k);
                int order = 0;
                for (String path : popularPathes) {
                    System.out.println (order++ + ":" + path);
                }

                topNPopularPathService.getTopNPopularPathsAmount (k).entrySet ( ).stream ( ).forEach
                        (entry -> {
                            System.out.printf ("path: %s, visit amount: %d\n", entry.getKey ( ), entry
                                    .getValue ( ));
                        });
            } else {
                System.err.println ("Invalid input");
            }
        }
    }
}
