package org.leeq.algorithm;

import org.leeq.algorithm.impl.TopNPopularPathServiceImpl;
import org.leeq.algorithm.visitsimulator.SimulatorUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;

/**
 * Created by liqingsong on 28/05/2017.
 */
public class TopNPopularPathServiceTest {

    @Test
    public void createVisitLogFile() {
        Path path = SimulatorUtil.createVisitLogFile (1000000, 100);
        Assert.assertTrue ( "File created failed", path != null);
    }

    @Test
    public void topNpopularPathTest() throws IOException {
        TopNPopularPathService topNPopularPathService = new TopNPopularPathServiceImpl ( );
        topNPopularPathService.setup (SimulatorUtil.getVistiData (1000000, 100));

        String[] popularPathes = topNPopularPathService.getTopNPopularPaths (10);
        for (String path : popularPathes) {
            System.out.println (path);
        }

        topNPopularPathService.getTopNPopularPathsAmount (10).entrySet ( ).stream ( ).forEach
                (entry -> {
                    System.out.printf ("path: %s, visit amount: %d\n", entry.getKey ( ), entry
                            .getValue ( ));
                });
    }


    @Test
    @Deprecated
    public void generateRandomWords() {
        String[] randomStrings = new String[3];
        Random random = new Random ( );
        for (int i = 0; i < 3; i++) {
            char[] word = new char[random.nextInt (8) + 3]; // words of length 3 through 10. (1
            // and 2 letter words are boring.)
            for (int j = 0; j < word.length; j++) {
                word[j] = (char) ('a' + random.nextInt (26));
            }
            randomStrings[i] = new String (word);
            System.out.println (word);
        }

    }
}
