package org.leeq.algorithm;

import java.util.Map;

/**
 * Created by liqingsong on 27/05/2017.
 */
public interface TopNPopularPathService {
    //Data initial
    void setup(String[][] data);

    //Get top n popular paths without visit amount
    String[] getTopNPopularPaths(int n);

    //Get top n popular paths with visit amount
    Map<String, Integer> getTopNPopularPathsAmount(int n);
}
