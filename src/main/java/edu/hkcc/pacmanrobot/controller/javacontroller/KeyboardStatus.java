package edu.hkcc.pacmanrobot.controller.javacontroller;

/**
 * Created by beenotung on 3/23/15.
 */
public class KeyboardStatus {
    public Integer code;
    public boolean isPressed=false;

    public KeyboardStatus(Integer code) {
        this.code = code;
    }
}
