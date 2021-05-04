package org.game.game2d.particle;

import org.game.game2d.Sprite_2942625;
import org.game.game2d.Vector2_2942625;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.game.game2d.particle.Particle_2942625.INIT_RADIUS;

/**
 * a particle generator which generates tail effects for the moving ball in this game.
 */
public class ParticleGenerator_2942625 {
    private Random random =  new Random();
    public static final int INSTANCES = 100;
    private static final int NEW_PARTICLE_EACH_TIME = 1;
    private int usedIndex = 0;
    private List<Particle_2942625> particleList = new ArrayList<>();
    private BufferedImage image;

    public ParticleGenerator_2942625(String imgPath) {
        try {
            image =ImageIO.read(new File(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < INSTANCES; i++) {
            particleList.add(new Particle_2942625(i));
        }
    }
    public void draw(Graphics2D g){
        Color gColor = g.getColor();
        for (Particle_2942625 pa:particleList) {
            if(pa.life > 0.f){
                Vector2_2942625 position = pa.position;
                Color color = pa.color;
                g.setColor(color);
                g.drawOval((int)position.getX(),(int)position.getY(),(int)pa.radius,(int)pa.radius );
            }
        }
        g.setColor(gColor);
    }
    public void update(long elapsedTime, Sprite_2942625 origin, Vector2_2942625 offset){
        respawn(origin,offset);
        updateAllParticleStates(elapsedTime);
    }

    private void updateAllParticleStates(long elapsedTime) {
        for (Particle_2942625 pa: particleList) {
            pa.life -= elapsedTime *0.2f;
            if (pa.life > 0.0f)
            {
                float lifePercentage = pa.life / 100.f;
                pa.radius = lifePercentage * INIT_RADIUS;
                pa.position = pa.position.subtract(pa.velocity.multiply(elapsedTime*0.7f));
                int red =  pa.color.getRed();
                int green = pa.color.getGreen();
                int blue = pa.color.getBlue();
                int alpha = (int) (Math.max(0,lifePercentage-0.3) * 255);
                pa.color = new Color(red,green,blue,alpha);
            }
        }
    }


    /**
     * respawn particles with random position and color.
     * slower speed than the origin game objects.
     * @param gameObj
     * @param offset
     */
    private void respawn(Sprite_2942625 gameObj, Vector2_2942625 offset){
        for (int i = 0; i < NEW_PARTICLE_EACH_TIME; i++) {
            Particle_2942625 particle = findLastUsedParticles();
            float randomPos = ( this.random.nextInt(60) - 30)/10.f-8;
            float randomR =  this.random.nextInt(100) / 100.0f;
            particle.position = gameObj.getPosition().add(randomPos).add(offset).
                    subtract(gameObj.getVelocity().multiply(60));
            particle.color =new Color(randomR,  randomR,randomR,1.f);
            particle.life = 100;
            particle.velocity = gameObj.getVelocity().multiply(3.f);
            particle.radius = INIT_RADIUS;
        }
    }

    /**
     * find last used particle in performance.
     * if it doesn't exist, will return the first one.
     @return
     */
    private Particle_2942625 findLastUsedParticles() {
        for ( int i = usedIndex; i < particleList.size(); i++) {
            if (particleList.get(i).life<= 0.0f){
                usedIndex = i;
                return particleList.get(i);
            }
        }
        for (int i = 0; i < usedIndex; ++i) {
            if (particleList.get(i).life<= 0.0f){
                usedIndex = i;
                return particleList.get(i);
            }
        }
        usedIndex = 0 ;
        return particleList.get(usedIndex);
    }


}
