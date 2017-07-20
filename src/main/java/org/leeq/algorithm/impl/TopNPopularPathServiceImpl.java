package org.leeq.algorithm.impl;

import org.leeq.algorithm.TopNPopularPathService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by liqingsong on 27/05/2017.
 */
public class TopNPopularPathServiceImpl implements TopNPopularPathService {
    //Path linking symbol
    private static final String PATH_SEPERATOR = "->";

    private static final Integer PATHS_NUM_IN_SEQUENTIAL = 3;

    //Cache sorted path in decent order by using path as key, visit count as value.
    private Map<String, Integer> sortedSequentialPath;

    @Override
    public void setup(String[][] data) {

//        Assert.that (data != null, "Input data is null");

        //The visit amount for all paths.
        int row = data.length;

        //Key: user, for value there are 3 cases: one path, two adjacent paths or three paths as
        // a sequential. e.g. ('U1', '/->subscribers').
        Map<String, List> userPathMap = new HashMap<> ( );

        //Key: sequential path, value: visit amount. e.g. ('/->subscribers->filter',2).
        Map<String, Integer> pathVisitCountMap = new HashMap<> ( );

        //Temporary variables for the data traversal
        String userTmp;
        String pathTmp;
        String sequentialPathTmp;
        Integer countTmp;
        List pathListTmp;

        for (int i = 0; i < row; i++) {
            userTmp = data[i][0];
            pathTmp = data[i][1];
            //Obtain current path list of the user to check value's case mentioned above.
            pathListTmp = userPathMap.get (userTmp) == null ? new LinkedList ( )
                    : userPathMap.get (userTmp);

            //Add current path into list
            pathListTmp.add (pathTmp);

            //This is a sequential path
            if (pathListTmp.size ( ) == PATHS_NUM_IN_SEQUENTIAL) {
                //Convert paths to string
                sequentialPathTmp = String.join (PATH_SEPERATOR, pathListTmp);

                //Add or update the sequential path's visit count
                countTmp = pathVisitCountMap.get (sequentialPathTmp) == null ? 0 :
                        pathVisitCountMap.get
                                (sequentialPathTmp);
                pathVisitCountMap.put (sequentialPathTmp, ++countTmp);

                //Keep two latest path by removing the first path from path list
                pathListTmp.remove (0);
            } else {
                userPathMap.put (userTmp, pathListTmp);
            }
        }

        //Sort sequential paths by visit amount in descent order
        sortedSequentialPath = pathVisitCountMap.entrySet ( )
                .stream ( )
                .sorted (Map.Entry.comparingByValue (Collections.reverseOrder ( )))
                .collect (Collectors.toMap (
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

    }

    @Override
    public String[] getTopNPopularPaths(int n) {
//        Assert.that (sortedSequentialPath != null, "Sorted pathes data is null");
        int i = n >= sortedSequentialPath.size ( ) ? sortedSequentialPath.size ( ) : n;
        List<String> list = sortedSequentialPath.entrySet ( ).stream ( ).limit (i).map (Map
                .Entry::getKey).collect
                (Collectors.toList ( ));
        return list.toArray (new String[list.size ( )]);
    }

    @Override
    public Map<String, Integer> getTopNPopularPathsAmount(int n) {
//        Assert.that (sortedSequentialPath != null, "Sorted pathes data is null");
        int i = n >= sortedSequentialPath.size ( ) ? sortedSequentialPath.size ( ) : n;

        return sortedSequentialPath.entrySet ( ).stream ( ).limit (i).collect (Collectors.toMap
                (m -> m.getKey ( ), m -> m.getValue ( ), (u, v) -> {
                            throw new IllegalStateException (String.format ("Duplicate key %s", u));
                        },
                        LinkedHashMap::new));
    }
}
