package org.game.breakout;

import org.game.game2d.Sprite;
import org.game.utils.GlobalUtils;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * unfinished feature.
 *
 * */
public class PowerUpGenerator {

    private List<PowerUp> powerUpList =new ArrayList<>();
    String [] typeImages = new String[]{"images/particle.png","images/particle.png","images/particle.png"};
    private static final PowerUpGenerator singleton = new PowerUpGenerator();
    private PowerUpGenerator(){
    }

    public void spawnPowerUp(PowerUp.Type type, Sprite sprite){
        int ordinal = type.ordinal();
        String typeImage = typeImages[ordinal];
        PowerUp powerUp = new PowerUp(typeImage, type, 50, 20);
        powerUp.setPosition(sprite.getX(),sprite.getY());

    }
    public static PowerUpGenerator getInstance(){
        return singleton;
    }

    public void update(long elapse){
       powerUpList.stream().filter(powerUp -> powerUp.getY()< GlobalUtils.screenHeight && !powerUp.isActive());
        powerUpList.stream().forEach(powerUp -> powerUp.update(elapse));
    }

    public void draw(Graphics2D g){
        powerUpList.stream().forEach(powerUp -> powerUp.draw(g));
    }
}
