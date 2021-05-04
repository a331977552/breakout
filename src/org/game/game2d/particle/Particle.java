package org.game.game2d.particle;

import org.game.game2d.Vector2;

import java.awt.*;

/**
 * a particle class.
 * when life drops to 0, it is dead object.
 * reset position , velocity, and life will make it live again.
 */
public class Particle {
    public float life;
    public Vector2 position;
    public Vector2 velocity;
    public Color color;
    public int index;
    public static final int INIT_RADIUS = 20;
    public float radius = INIT_RADIUS;

    public Particle(float life, Vector2 position, Vector2 velocity, Color color) {
        this.life = life;
        this.position = position;
        this.velocity = velocity;
        this.color = color;
    }

    public Particle() {
        this.life = 0.f;
        this.position = new Vector2();
        this.velocity = new Vector2();
        this.color = Color.WHITE;
    }

    public Particle(int index) {
        this.index = index;
    }
}
