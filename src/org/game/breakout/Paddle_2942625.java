package org.game.breakout;

import org.game.game2d.Animation_2942625;
import org.game.game2d.Sprite_2942625;
import org.game.utils.ImageUtil_2942625;

import java.awt.*;

public class Paddle_2942625 extends Sprite_2942625 {


    public static final int HEIGHT = 22;
    public static final int WIDTH = 120;
    public static final float VELOCITY_X = 1f;
    /**
     * Creates a new Sprite object with the specified Animation.
     */
    public Paddle_2942625() {
        super(new Animation_2942625());
        Image scaledImage = ImageUtil_2942625.loadImage("images/paddle.png",WIDTH, HEIGHT);
        getAnimation().addFrame(scaledImage, 1000);
    }

    @Override
    public void update(long elapsedTime) {
        if (!this.isVisible())
            return;

    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
//        drawBoundingBox(g);
    }
}
