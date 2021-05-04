package org.game.breakout;

import org.game.game2d.Animation_2942625;
import org.game.game2d.Sprite_2942625;
import org.game.utils.Env_2942625;
import org.game.utils.GlobalUtils_2942625;
import org.game.utils.ImageUtil_2942625;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ball_2942625 extends Sprite_2942625 {
    /**
     * Creates a new Sprite object with the specified Animation.
     *
     * @param anim
     */
    public static final int RADIUS = 13;
    public static final float INIT_VELOCITY_Y = -0.4f;
    public static final float INIT_VELOCITY_X = 0.1f;
    public static final float COLLISION_STRENGTH = 2f;

    public boolean isLaunched() {
        return launched;
    }

    public void setLaunched(boolean launched) {
        this.launched = launched;
    }

    private boolean launched = false;
    public Ball_2942625() {
        super(new Animation_2942625());
        getAnimation().addFrame(ImageUtil_2942625.loadImage("images/ball_1.png",RADIUS*2 ,RADIUS*2), 50);
        getAnimation().addFrame(ImageUtil_2942625.loadImage("images/ball_2.png",RADIUS*2 ,RADIUS*2), 50);
        getAnimation().addFrame(ImageUtil_2942625.loadImage("images/ball_3.png",RADIUS*2 ,RADIUS*2), 50);
        getAnimation().addFrame(ImageUtil_2942625.loadImage("images/ball_4.png",RADIUS*2 ,RADIUS*2), 50);
        getAnimation().addFrame(ImageUtil_2942625.loadImage("images/ball_5.png",RADIUS*2 ,RADIUS*2), 50);
        getAnimation().addFrame(ImageUtil_2942625.loadImage("images/ball_6.png",RADIUS*2 ,RADIUS*2), 50);
        getAnimation().addFrame(ImageUtil_2942625.loadImage("images/ball_4.png",RADIUS*2 ,RADIUS*2), 50);
        getAnimation().addFrame(ImageUtil_2942625.loadImage("images/ball_3.png",RADIUS*2 ,RADIUS*2), 50);
        getAnimation().addFrame(ImageUtil_2942625.loadImage("images/ball_2.png",RADIUS*2 ,RADIUS*2), 50);
        getAnimation().addFrame(ImageUtil_2942625.loadImage("images/ball_1.png",RADIUS*2 ,RADIUS*2), 50);
    }

    @Override
    public void update(long elapsedTime) {
        if(!isVisible() ||!isLaunched())
            return;
        moving(elapsedTime);
        this.getAnimation().update(elapsedTime);
    }

    /**
     * reset ball to initial state.
     * @param x
     * @param y
     * @param velocityX
     * @param velocityY
     */
    public void resetBall(float x,float y,float velocityX,float velocityY){
        this.setLaunched(false);
        this.setPosition(x, y);
        this.setVelocity(velocityX, velocityY);
    }

    /**
     * move ball and bounce if necessary
     * @param elapsedTime
     */
    private void moving(long elapsedTime) {
            float offsetX = elapsedTime * this.getVelocityX();
            float offsetY = elapsedTime * this.getVelocityY();

            setPositionBy(offsetX, offsetY);

            float x = this.getX();
            float y = this.getY();


            //screen edge left check
            if(x<=0){
                //Bounce back from left
                this.setVelocityX(-this.getVelocityX());
                this.setX(0);
            }
            //screen edge right check
            else if (x> GlobalUtils_2942625.screenWidth - this.getWidth()){
                //bounce back from right
                this.setVelocityX(-this.getVelocityX());
                this.setX(GlobalUtils_2942625.screenWidth - this.getWidth());
            }
            //screen top check
            if(y<=0)
            {
                //Bounce back from top
                this.setVelocityY(-this.getVelocityY());
                this.setY(0);
            }
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        if (Env_2942625.LOG_LEVEL == Env_2942625.DEBUG)
            drawCircleBox(g);
    }
    /**
     * Draws the bounding box of this sprite using the graphics object 'g' and
     * the currently selected foreground colour.
     */
    public void drawCircleBox(Graphics2D g) {
        if (!this.isVisible()) return;
        float x = this.getX();
        float y = this.getY();
        Image img = getImage();
        Ellipse2D.Double circle = new Ellipse2D.Double(x, y, img.getWidth(null), img.getHeight(null));
        g.draw(circle);
    }
}
