package org.game.breakout;

import org.game.game2d.Animation;
import org.game.game2d.Sprite;
import org.game.utils.ImageUtil;

import java.awt.*;

public class Paddle extends Sprite {


    public static final int HEIGHT = 22;
    public static final int WIDTH = 120;
    public static final float VELOCITY_X = 1f;
    /**
     * Creates a new Sprite object with the specified Animation.
     */
    public Paddle() {
        super(new Animation());
        Image scaledImage = ImageUtil.loadImage("images/paddle.png",WIDTH, HEIGHT);
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
