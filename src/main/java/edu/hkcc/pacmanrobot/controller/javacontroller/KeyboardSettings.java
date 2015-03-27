package edu.hkcc.pacmanrobot.controller.javacontroller;

import java.awt.event.KeyEvent;

/**
 * Created by beenotung on 3/23/15.
 */
public class KeyboardSettings {
    public static final String MODE_ARROW = "ARROW";
    public static final String MODE_WSAD = "WSAD";
    public Integer up, down, left, right;

    public KeyboardSettings() {
        this(MODE_ARROW);
    }
    public KeyboardSettings(String mode) {
        switch (mode) {
            case MODE_ARROW:
                up = KeyEvent.VK_UP;
                down = KeyEvent.VK_DOWN;
                left = KeyEvent.VK_LEFT;
                right = KeyEvent.VK_RIGHT;
                break;
            case MODE_WSAD:
                up = KeyEvent.VK_W;
                down = KeyEvent.VK_S;
                left = KeyEvent.VK_A;
                right = KeyEvent.VK_D;
        }
    }

    public KeyboardSettings(int up, int down, int left, int right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }
}
