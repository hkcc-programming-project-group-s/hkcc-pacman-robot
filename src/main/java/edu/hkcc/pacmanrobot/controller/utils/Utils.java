package edu.hkcc.pacmanrobot.controller.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Utils {

    public static ImageIcon getImageIcon(String url) throws MalformedURLException, IOException {
        // TODO Auto-generated method stub
        BufferedImage image = ImageIO.read(new URL(url));
        return new ImageIcon(image);
    }

}
