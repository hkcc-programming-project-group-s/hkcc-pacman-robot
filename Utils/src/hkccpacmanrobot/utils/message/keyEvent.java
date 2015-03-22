/**
 * Created by 13058456a on 3/21/2015.
 */
import java.awt.*;
import java.awt.event.*;

class KeyEventDemo extends KeyAdapter {
    Frame frame;
    TextArea text;

    public static void main(String[] args) {
        new KeyEventDemo();
    }

    public KeyEventDemo() {
        frame = new Frame("AWTDemo");
        frame.addWindowListener(new AdapterDemo());
        frame.setSize(600, 400);
        text = new TextArea();
        text.addKeyListener(this);
        frame.add(text);
        frame.setVisible(true);
    }

    public void keyPressed(KeyEvent e) {
        int key1 = e.getKeyCode();
        char key2 = e.getKeyChar();

        switch (key1) {
            case KeyEvent.VK_ESCAPE:
                System.out.println("esc");
                break;

            case KeyEvent.VK_TAB:
                System.out.println("tab");
                break;

            case KeyEvent.VK_CAPS_LOCK:
                System.out.println("caps");
                break;

            case KeyEvent.VK_SHIFT:
                System.out.println("shift");
                break;

            case KeyEvent.VK_CONTROL:
                System.out.println("control or ctrl");
                break;

            case KeyEvent.VK_ALT:
                System.out.println("option or alt");
                break;

            case KeyEvent.VK_WINDOWS:
                System.out.println("window");
                break;

            case 157:
                System.out.println("command");
                break;

            case KeyEvent.VK_SPACE:
                System.out.println("space");
                break;

            case KeyEvent.VK_DELETE:
                System.out.println("delete");
                break;

            case KeyEvent.VK_BACK_SPACE:
                System.out.println("backspace");
                break;

            case KeyEvent.VK_ENTER:
                System.out.println("return or enter");
                break;

            case KeyEvent.VK_HOME:
                System.out.println("home");
                break;

            case KeyEvent.VK_END:
                System.out.println("end");
                break;

            case KeyEvent.VK_PAGE_UP:
                System.out.println("page up");
                break;

            case KeyEvent.VK_PAGE_DOWN:
                System.out.println("page down");
                break;

            case KeyEvent.VK_UP:
                System.out.println("up");
                break;

            case KeyEvent.VK_LEFT:
                System.out.println("left");
                break;

            case KeyEvent.VK_DOWN:
                System.out.println("down");
                break;

            case KeyEvent.VK_RIGHT:
                System.out.println("right");
                break;

            case KeyEvent.VK_CLEAR:
                System.out.println("clear");
                break;

            default:
                System.out.println(key2);
        }
    }
}

class AdapterDemo extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}