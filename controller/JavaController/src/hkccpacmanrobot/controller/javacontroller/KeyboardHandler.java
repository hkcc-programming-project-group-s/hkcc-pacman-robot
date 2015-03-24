package hkccpacmanrobot.controller.javacontroller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import hkccpacmanrobot.utils.Maths.Point2D;


/**
 * Created by beenotung on 3/23/15.
 */
public class KeyboardHandler extends KeyAdapter {
    private final KeyboardSettings settings;
    public KeyboardStatus up, down, left, right;

    public KeyboardHandler(KeyboardSettings settings) {
        this.settings = settings;
        up = new KeyboardStatus(settings.up);
        down = new KeyboardStatus(settings.down);
        left = new KeyboardStatus(settings.left);
        right = new KeyboardStatus(settings.right);
    }

    public KeyboardStatus getObject(Integer code) throws UnsupportedOperationException {
        if (code.equals(up.code))
            return up;
        else if (code.equals(down.code))
            return down;
        else if (code.equals(left.code))
            return left;
        else if (code.equals(right.code))
            return right;
        else throw new UnsupportedOperationException();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        try {
            getObject(e.getKeyCode()).isPressed = true;
        } catch (UnsupportedOperationException ex) {
            // undefined operation
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        try {
            getObject(e.getKeyCode()).isPressed = false;
        } catch (UnsupportedOperationException ex) {
            // undefined operation
        }
    }

    public Point2D getDirection() {
        double distance = 1;
        double direction = 0;

        //check for break
        if (up.isPressed && down.isPressed)
            distance = 0;
        else if (left.isPressed && right.isPressed)
            distance = 0;
            //check for normal movement
        else if (up.isPressed && right.isPressed)
            direction = Math.PI * 0.25;
        else if (down.isPressed && right.isPressed)
            direction = Math.PI * 0.75;
        else if (down.isPressed && left.isPressed)
            direction = Math.PI * 1.25;
        else if (up.isPressed && left.isPressed)
            direction = Math.PI * 1.75;
        else if (up.isPressed)
            direction = 0d;
        else if (right.isPressed)
            direction = Math.PI * 0.5;
        else if (down.isPressed)
            direction = Math.PI;
        else if (left.isPressed)
            direction = Math.PI * 1.5;
            // when no movement
        else distance = 0;
        return new Point2D(direction, distance);
    }
}
