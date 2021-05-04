package org.game.game2d;

import org.game.utils.Env;
import org.game.utils.ImageUtil;

import java.awt.Image;
import java.awt.*;
import java.awt.geom.*;

/**
 * This class provides the functionality for a moving animated image or Sprite.
 *
 * @author David Cairns
 */
public class Sprite {

    // The current Animation to use for this sprite
    private Animation anim;

    // Position (pixels)
    private Vector2 position;
    private Vector2 velocity;// Velocity (pixels per millisecond)


    // Dimensions of the sprite
    private float height;
    private float width;
    private float radius;

    // The scale to draw the sprite at where 1 equals normal size
    private double scale;
    // The rotation to apply to the sprite image
    private double rotation;

    // If render is 'true', the sprite will be drawn when requested
    private boolean render;

    // The draw offset associated with this sprite. Used to draw it
    // relative to specific on screen position (usually the player)
    private int xoff = 0;
    private int yoff = 0;

    /**
     * Creates a new Sprite object with the specified Animation.
     *
     * @param anim The animation to use for the sprite.
     */
    public Sprite(Animation anim) {
        this.anim = anim;
        render = true;
        scale = 1.0f;
        rotation = 0.0f;
        position = new Vector2();
        velocity = new Vector2();
    }
    /**
     * Creates a new Sprite object with the non-animated img.
     *
     * @param imagePath The animation to use for the sprite.
     */
    public Sprite(String  imagePath) {
        this.anim = new Animation();
        this.anim.addFrame(ImageUtil.loadImage(imagePath), 1000);
        render = true;
        scale = 1.0f;
        rotation = 0.0f;
        position = new Vector2();
        velocity = new Vector2();
    }
    /**
     * Creates a new Sprite object with  the sized and non-animated img.
     *
     * @param imagePath The animation to use for the sprite.
     */
    public Sprite(String  imagePath, int width, int height) {
        this.anim = new Animation();
        this.anim.addFrame(ImageUtil.loadImage(imagePath,width, height), 1000);
        render = true;
        scale = 1.0f;
        rotation = 0.0f;
        position = new Vector2();
        velocity = new Vector2();
    }


    /**
     * Change the animation for the sprite to 'a'.
     *
     * @param a The animation to use for the sprite.
     */
    public void setAnimation(Animation a) {
        anim = a;
    }

    /**
     * Set the current animation to the given 'frame'
     *
     * @param frame The frame to set the animation to
     */
    public void setAnimationFrame(int frame) {
        anim.setAnimationFrame(frame);
    }

    /**
     * Pauses the animation at its current frame. Note that the
     * sprite will continue to move, it just won't animate
     */
    public void pauseAnimation() {
        anim.pause();
    }

    /**
     * Pause the animation when it reaches frame 'f'.
     *
     * @param f The frame to stop the animation at
     */
    public void pauseAnimationAtFrame(int f) {
        anim.pauseAt(f);
    }

    /**
     * Change the speed at which the current animation runs. A
     * speed of 1 will result in a normal animation,
     * 0.5 will be half the normal rate and 2 will double it.
     * <p>
     * Note that if you change animation, it will run at whatever
     * speed it was previously set to.
     *
     * @param speed The speed to set the current animation to.
     */
    public void setAnimationSpeed(float speed) {
        anim.setAnimationSpeed(speed);
    }

    /**
     * Starts an animation playing if it has been paused.
     */
    public void playAnimation() {
        anim.play();
    }

    /**
     * Returns a reference to the current animation
     * assigned to this sprite.
     *
     * @return A reference to the current animation
     */
    public Animation getAnimation() {
        return anim;
    }

    /**
     * Updates this Sprite's Animation and its position based
     * on the elapsedTime.
     *
     * @param elapsedTime that has elapsed since the last call to update
     */
    public void update(long elapsedTime) {
        if (!render) return;
        float dx = this.velocity.getX();
        float dy = this.velocity.getY();
        this.setPositionBy(dx * elapsedTime, dy * elapsedTime);
        anim.update(elapsedTime);
        width = anim.getImage().getWidth(null);
        height = anim.getImage().getHeight(null);
        if (width > height)
            radius = width / 2.0f;
        else
            radius = height / 2.0f;
    }

    /**
     * Gets this Sprite's current x position.
     */
    public float getX() {
        return position.getX();
    }

    /**
     * Gets this Sprite's current y position.
     */
    public float getY() {
        return position.getY();
    }

    /**
     * Sets this Sprite's current x position.
     */
    public void setX(float x) {
        this.position.setX(x);
    }

    /**
     * Sets this Sprite's current y position.
     */
    public void setY(float y) {
        this.position.setY(y);
    }

    /**
     * Sets this Sprite's new x and y position.
     */
    public void setPosition(float x, float y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public void shiftX(float shift) {
        this.position.setX(this.position.getX() + shift);
    }

    public void shiftY(float shift) {
        this.position.setY(this.position.getY() + shift);
    }

    /**
     * Gets this Sprite's width, based on the size of the
     * current image.
     */
    public int getWidth() {
        return anim.getImage().getWidth(null);
    }

    /**
     * Gets this Sprite's height, based on the size of the
     * current image.
     */
    public int getHeight() {
        return anim.getImage().getHeight(null);
    }

    /**
     * Gets the sprites radius in pixels
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Gets the horizontal velocity of this Sprite in pixels
     * per millisecond.
     */
    public float getVelocityX() {
        return this.velocity.getX();
    }

    /**
     * Gets the vertical velocity of this Sprite in pixels
     * per millisecond.
     */
    public float getVelocityY() {
        return this.velocity.getY();
    }

    public Vector2 getVelocity() {
        return this.velocity;
    }


    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }


    /**
     * Sets the horizontal velocity of this Sprite in pixels
     * per millisecond.
     */
    public void setVelocityX(float dx) {
        this.velocity.setX(dx);
    }

    /**
     * Sets the vertical velocity of this Sprite in pixels
     * per millisecond.
     */
    public void setVelocityY(float dy) {
        this.velocity.setY(dy);
    }

    /**
     * Sets the horizontal and vertical velocity of this Sprite in pixels
     * per millisecond.
     */
    public void setVelocity(float dx, float dy) {
        this.velocity.setX(dx);
        this.velocity.setY(dy);
    }

    /**
     * Set the scale of the sprite to 's'. If s is 1
     * the sprite will be drawn at normal size. If 's'
     * is 0.5 it will be drawn at half size. Note that
     * scaling and rotation are only applied when
     * using the drawTransformed method.
     */
    public void setScale(float s) {
        scale = s;
    }

    public Vector2 getPosition() {
        return position;
    }

    /**
     * Get the current value of the scaling attribute.
     * See 'setScale' for more information.
     */
    public double getScale() {
        return scale;
    }

    /**
     * Set the rotation angle for the sprite in degrees.
     * Note that scaling and rotation are only applied when
     * using the drawTransformed method.
     */
    public void setRotation(double r) {
        rotation = Math.toRadians(r);
    }

    /**
     * Get the current value of the rotation attribute.
     * in degrees. See 'setRotation' for more information.
     */
    public double getRotation() {
        return Math.toDegrees(rotation);
    }

    /**
     * Stops the sprites movement at the current position
     */
    public void stop() {
        this.velocity = new Vector2();
    }

    /**
     * Gets this Sprite's current image.
     */
    public Image getImage() {
        return anim.getImage();
    }

    /**
     * Draws the sprite with the graphics object 'g' at
     * the current x and y co-ordinates. Scaling and rotation
     * transforms are NOT applied.
     */
    public void draw(Graphics2D g) {
        if (!render) return;
        float x = this.position.getX();
        float y = this.position.getY();
        g.drawImage(getImage(), (int) x + xoff, (int) y + yoff, null);
        if (Env.LOG_LEVEL == Env.DEBUG) {
            String msg = String.format("%d, %d", (int) x, (int) y);
            g.setColor(Color.RED);
            g.drawString(msg, x, y);
        }

    }

    /**
     * Draws the bounding box of this sprite using the graphics object 'g' and
     * the currently selected foreground colour.
     */
    public void drawBoundingBox(Graphics2D g) {
        if (!render) return;
        float x = this.position.getX();
        float y = this.position.getY();
        Image img = getImage();
        g.drawRect((int) x, (int) y, img.getWidth(null), img.getHeight(null));
    }

    /**
     * Draws the bounding circle of this sprite using the graphics object 'g' and
     * the currently selected foreground colour.
     */
    public void drawBoundingCircle(Graphics2D g) {
        if (!render) return;

        Image img = getImage();
        float x = this.position.getX();
        float y = this.position.getY();
        g.drawArc((int) x, (int) y, img.getWidth(null), img.getHeight(null), 0, 360);
    }

    /**
     * Draws the sprite with the graphics object 'g' at
     * the current x and y co-ordinates with the current scaling
     * and rotation transforms applied.
     *
     * @param g The graphics object to draw to,
     */
    public void drawTransformed(Graphics2D g) {
        if (!render) return;
        float x = this.position.getX();
        float y = this.position.getY();
        AffineTransform transform = new AffineTransform();
        transform.translate(Math.round(x) + xoff, Math.round(y) + yoff);
        transform.scale(scale, scale);
        transform.rotate(rotation, getImage().getWidth(null) / 2, getImage().getHeight(null) / 2);
        // Apply transform to the image and draw it
        g.drawImage(getImage(), transform, null);
    }


    /**
     * Hide the sprite.
     */
    public void hide() {
        render = false;
    }

    /**
     * Show the sprite
     */
    public void show() {
        render = true;
    }

    /**
     * Check the visibility status of the sprite.
     */
    public boolean isVisible() {
        return render;
    }

    /**
     * move current object by x,y
     *
     * @param x
     * @param y
     */
    public void setPositionBy(float x, float y) {
        setPosition(this.getX() + x, this.getY() + y);
    }

    /**
     * Set an x & y offset to use when drawing the sprite.
     * Note this does not affect its actual position, just
     * moves the drawn position.
     */
    public void setOffsets(int x, int y) {
        xoff = x;
        yoff = y;
    }


}
