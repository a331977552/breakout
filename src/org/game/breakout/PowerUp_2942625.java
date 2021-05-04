package org.game.breakout;

import org.game.game2d.Sprite_2942625;
import org.game.game2d.Vector2_2942625;

public  class PowerUp_2942625 extends Sprite_2942625 {

    public enum Type{
        PASS_THROUGH, //ball can pass through the brick
        SPEED,//SPEED up the ball
        STICKY,//ball can stick on the paddle
        MUTIPLE //two balls will be on the screen
    }
    public static final Vector2_2942625 INIT_VELOCITY= new Vector2_2942625(0,0.1f);

    private Type type;
    private boolean active;
    private int duration;


    public PowerUp_2942625(String imagePath, Type type, int width, int height) {
            super(imagePath, width, height);
            this.type = type;
            setVelocity(INIT_VELOCITY);
    }

    public Type getType() {
        return type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    }
