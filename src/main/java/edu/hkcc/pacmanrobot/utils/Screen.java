package edu.hkcc.pacmanrobot.utils;

/**
 * Created by beenotung on 3/30/15.
 */
public class Screen {
    public final static void goto11() {
        System.out.print((char) 27 + "[1;1H");
    }

    public final static void clearConsole() {
        System.out.print((char) 27 + "[2J");
        goto11();
    }
}
