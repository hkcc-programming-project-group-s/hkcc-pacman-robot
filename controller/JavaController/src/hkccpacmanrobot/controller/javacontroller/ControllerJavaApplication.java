package hkccpacmanrobot.controller.javacontroller;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;

public class ControllerJavaApplication {
    public JFrame mainFrame;
    private JTextArea jTextArea;

    public void setup(){
        mainFrame=new JFrame();
        mainFrame.setVisible(false);
        jTextArea = new JTextArea();
        mainFrame.getContentPane().add(jTextArea);
    }
    public ControllerJavaApplication(){
     setup();
    }

    public void start (){
mainFrame.setVisible(true);
    }
}
