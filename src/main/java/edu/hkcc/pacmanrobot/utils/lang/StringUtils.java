package edu.hkcc.pacmanrobot.utils.lang;

/**
 * Created by beenotung on 4/23/15.
 */
public class StringUtils {
    public static String fill(char c, int count) {
        StringBuilder result = new StringBuilder();
        while (result.length() < count)
            result.append(c);
        return result.toString();
    }
}
