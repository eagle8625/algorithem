package com.leetcode.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liqingsong on 27/07/2017.
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/tabs/description/
 * Get the longest substring's length of input string
 */
public class LongestSubString {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || "".equals (s.trim ( ))) {
            return 0;
        }
        if (s.length ( ) == 1) {
            return 1;
        }

        HashMap<Character, Integer> charIdxMap = new HashMap<> ( );
        int max = 0, i = 0, j = 0;

        List<Character> listToRemove;
        do {
            Character start = s.charAt (i);
            Integer idx = charIdxMap.get (start);
            if (idx == null) {
                charIdxMap.put (s.charAt (i), i++);
                j++;
            } else {
                if (max < j) {
                    max = j;
                }
                listToRemove = new ArrayList<> ();
                for(Map.Entry<Character, Integer> entry: charIdxMap.entrySet ()){
                    if(entry.getValue () < idx){
                        listToRemove.add (entry.getKey ());
                    }
                }

                //replace the old index
                charIdxMap.put (start, i);

                for(Character c : listToRemove){
                    charIdxMap.remove (c);
                    j -= 1;
                }
                i += 1;
            }
        } while (i < s.length ( ));
        return max > j ? max : j;
    }
}
