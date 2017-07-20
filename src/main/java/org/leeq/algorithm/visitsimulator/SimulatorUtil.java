package org.leeq.algorithm.visitsimulator;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

/**
 * Created by liqingsong on 29/05/2017.
 */
public class SimulatorUtil {
    public static final String[] PATH_DICTIONARY = {"/", "subscribers", "filter", "catalog",
            "edit", "delete", "update", "export", "pay", "invoice", "rate", "help", "search",
            "product", "bill", "charge", "provider", "account", "seller", "dun", "provision"};

    public static final String USER_PREFIX = "USER";
    public static final String USER_PATH_SEPERATOR = ":";
    public static final String dir = System.getProperty ("user.dir");

    /**
     * Create visit log mock file.
     *
     * @param visitAmount
     * @param userNum
     * @return
     */
    public static Path createVisitLogFile(int visitAmount, int userNum) {
        Path file = FileSystems.getDefault ( ).getPath (dir,
                "visit.log");
        Charset charset = Charset.forName ("UTF-8");
        StringBuilder line = new StringBuilder ( );
        Random randomUserNo = new Random ( );
        Random randomPathNo = new Random ( );
        try (BufferedWriter writer = Files.newBufferedWriter (file, charset)) {
            for (int n = 0; n < visitAmount; n++) {
                int randomNum = randomUserNo.nextInt (userNum);
                line.append (USER_PREFIX).append (randomNum).append (USER_PATH_SEPERATOR).append
                        (PATH_DICTIONARY[randomPathNo.nextInt (PATH_DICTIONARY.length)]);
                writer.write (line.toString ( ), 0, line.length ( ));
                writer.newLine ( );
                line.delete (0, line.length ( ));
            }
            return file;
        } catch (IOException x) {
            System.err.format ("IOException: %s%n", x);
        }
        return null;
    }

    /**
     * Get (user, path) array
     *
     * @param visitAmount visit total paths number
     * @param userNum     visit user count
     * @return
     */
    public static String[][] getVistiData(int visitAmount, int userNum) {
        Path visitLogFile = createVisitLogFile (visitAmount, userNum);
        Charset charset = Charset.forName ("UTF-8");
        String[][] data = new String[visitAmount][2];

        try (BufferedReader reader = Files.newBufferedReader (visitLogFile, charset)) {
            String line;
            int i = 0;
            String[] pair;
            while ((line = reader.readLine ( )) != null) {
                pair = line.split (USER_PATH_SEPERATOR);
                data[i][0] = pair[0];
                data[i][1] = pair[1];
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace ( );
        }
        return data;
    }

}
