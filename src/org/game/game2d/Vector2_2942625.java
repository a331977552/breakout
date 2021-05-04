package org.game.game2d;

public class Vector2_2942625 {
    private float x;
    private float y;

    public Vector2_2942625(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2_2942625() {
    }

    @Override
    public String toString() {
        return "x=" + x +", y=" + y+" ";
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    /**
     * return a new vector which is the sum  of param vector and this vector
     * @param vector2
     * @return
     */
    public Vector2_2942625 add(Vector2_2942625 vector2){
        return new Vector2_2942625(this.x+vector2.x,this.y+vector2.y);
    }
    /**
     * return a new vector which is the sum  of length and this vector
     * @param length
     * @return
     */
    public Vector2_2942625 add(float length){
        return new Vector2_2942625(this.x+length,this.y+length);
    }

    /**
     * return the scalar value of dot
     * @param vector2
     * @return
     */
    public float dot(Vector2_2942625 vector2) {
        return this.x* vector2.x+this.y* vector2.y;
    }

    /**
     * return a new normalized vector
     * @return
     */
    public Vector2_2942625 normalize() {

        double sqrt = Math.sqrt(x * x + y * y);
        float newX = (float) (x / sqrt);
        float newY = (float) (y / sqrt);
        return new Vector2_2942625(newX,newY);
    }

    public float length() {
        return (float) Math.sqrt(x*x+y*y);
    }

    public Vector2_2942625 multiply(float factor) {
        return new Vector2_2942625(this.x * factor,this.y * factor);
    }

    public Vector2_2942625 subtract(Vector2_2942625 vector2) {
        return new Vector2_2942625(this.x- vector2.x,this.y - vector2.y);
    }
}
