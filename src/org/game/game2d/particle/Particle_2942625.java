package org.game.game2d.particle;

import org.game.game2d.Vector2_2942625;

import java.awt.*;

/**
 * a particle class.
 * when life drops to 0, it is dead object.
 * reset position , velocity, and life will make it live again.
 */
public class Particle_2942625 {
    public float life;
    public Vector2_2942625 position;
    public Vector2_2942625 velocity;
    public Color color;
    public int index;
    public static final int INIT_RADIUS = 20;
    public float radius = INIT_RADIUS;

    public Particle_2942625(float life, Vector2_2942625 position, Vector2_2942625 velocity, Color color) {
        this.life = life;
        this.position = position;
        this.velocity = velocity;
        this.color = color;
    }

    public Particle_2942625() {
        this.life = 0.f;
        this.position = new Vector2_2942625();
        this.velocity = new Vector2_2942625();
        this.color = Color.WHITE;
    }

    public Particle_2942625(int index) {
        this.index = index;
    }
}
