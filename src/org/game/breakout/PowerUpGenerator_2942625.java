package org.game.breakout;

import org.game.game2d.Sprite_2942625;
import org.game.utils.GlobalUtils_2942625;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * unfinished feature.
 *
 * */
public class PowerUpGenerator_2942625 {

    private List<PowerUp_2942625> powerUpList =new ArrayList<>();
    String [] typeImages = new String[]{"images/particle.png","images/particle.png","images/particle.png"};
    private static final PowerUpGenerator_2942625 singleton = new PowerUpGenerator_2942625();
    private PowerUpGenerator_2942625(){
    }

    public void spawnPowerUp(PowerUp_2942625.Type type, Sprite_2942625 sprite){
        int ordinal = type.ordinal();
        String typeImage = typeImages[ordinal];
        PowerUp_2942625 powerUp = new PowerUp_2942625(typeImage, type, 50, 20);
        powerUp.setPosition(sprite.getX(),sprite.getY());

    }
    public static PowerUpGenerator_2942625 getInstance(){
        return singleton;
    }

    public void update(long elapse){
       powerUpList.stream().filter(powerUp -> powerUp.getY()< GlobalUtils_2942625.screenHeight && !powerUp.isActive());
        powerUpList.stream().forEach(powerUp -> powerUp.update(elapse));
    }

    public void draw(Graphics2D g){
        powerUpList.stream().forEach(powerUp -> powerUp.draw(g));
    }
}
