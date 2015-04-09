package edu.hkcc.pacmanrobot.launcher;

import edu.hkcc.pacmanrobot.controller.gamecontroller.MiniMap;

/**
 * Created by beenotung on 4/8/15.
 */
public class MiniMapLauncher {
    public static void main(String[] args) {
        MiniMap miniMap = new MiniMap(800,600);
        miniMap.start();
    }
}
