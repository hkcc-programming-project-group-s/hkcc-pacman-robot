package edu.hkcc.pacmanrobot.controller.desktopcontroller;

import javax.swing.*;

public class ControllerJavaApplication {//extends Controller {
    public JFrame mainFrame;
    public KeyboardSettings keyboardSettings = new KeyboardSettings();
    public KeyboardHandler keyboardHandler = new KeyboardHandler(keyboardSettings);
    private JTextArea jTextArea;

    public ControllerJavaApplication() {
        super();
        setupGUI();
    }

    public void setupGUI() {
        mainFrame = new JFrame();
        mainFrame.setVisible(false);
        jTextArea = new JTextArea();
        mainFrame.getContentPane().add(jTextArea);
        mainFrame.setBounds(10, 10, 800, 600);
        jTextArea.addKeyListener(keyboardHandler);
    }


    public void start() {
        mainFrame.setVisible(true);
    }
}
