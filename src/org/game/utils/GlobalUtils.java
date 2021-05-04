package org.game.utils;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class GlobalUtils {
    public static int screenWidth;
    public static int screenHeight;
    /**
     * get string's with based on current font setting.
     * @param g
     * @param str
     * @return
     */
    private static Font initialFont;
    public static int getStringWidth(Graphics2D g, String str) {
        return g.getFontMetrics().stringWidth(str);
    }
    public static  Font getGameFont(){
        try {
            if(initialFont==null) {
                InputStream is = new FileInputStream("font/OCRAEXT.TTF");
                initialFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.BOLD).deriveFont(18.f);
            }
            return initialFont;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }


}
