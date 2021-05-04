package org.game.utils;

import javax.swing.*;
import java.awt.*;

public class ImageUtil_2942625 {

    public static Image loadImage(String filename){
        return    new ImageIcon(filename).getImage();
    }
    public static Image loadImage(String filename,int width,int height){
        return    new ImageIcon(filename).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }

}
