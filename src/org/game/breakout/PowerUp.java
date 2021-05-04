package org.game.breakout;

import org.game.game2d.Sprite;
import org.game.game2d.Vector2;

public  class PowerUp extends Sprite {

    public enum Type{
        PASS_THROUGH, //ball can pass through the brick
        SPEED,//SPEED up the ball
        STICKY,//ball can stick on the paddle
        MUTIPLE //two balls will be on the screen
    }
    public static final Vector2 INIT_VELOCITY= new Vector2(0,0.1f);

    private Type type;
    private boolean active;
    private int duration;


    public PowerUp(String imagePath, Type type, int width, int height) {
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
