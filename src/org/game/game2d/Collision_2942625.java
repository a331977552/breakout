package org.game.game2d;

import org.game.breakout.Ball_2942625;

/**
 * a convenient class for calculate collision between round object and rectangle object.
 */
public class Collision_2942625 {
    public enum Direction {
        LEFT(new Vector2_2942625(-1.f, 0.f)),
        UP(new Vector2_2942625(0.f, 1.f)),
        RIGHT(new Vector2_2942625(1.f, 0.f)),
        DOWN(new Vector2_2942625(0.f, -1.f));

        private final Vector2_2942625 vector2;

        Direction(Vector2_2942625 vector2) {
            this.vector2 = vector2;
        }
    }

    public static class CollisionInfo {
        private boolean isCollided;
        Direction direction;
        private Vector2_2942625 difference;

        public CollisionInfo(boolean isCollided, Direction direction, Vector2_2942625 difference) {
            this.isCollided = isCollided;
            this.direction = direction;
            this.difference = difference;
        }
        public static CollisionInfo CollisionFree(){
            return new CollisionInfo(false, null, null);
        }
        public boolean isCollided() {
            return isCollided;
        }

        public Direction getDirection() {
            return direction;
        }


        public Vector2_2942625 getDifference() {
            return difference;
        }

    }

    /**
     * calculate vector's direction so that we can determine which direction the ball
     * should bounce back to
     */
    static Direction calculateCollidingDirection(Vector2_2942625 vector2) {
        Direction[] mapDirection = new Direction[]{
                Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT
        };
        float max = 0.0f;
        int best_match = -1;

        Vector2_2942625 normalized = vector2.normalize();
        for (int i = 0; i < 4; i++) {
            //a.dot(b) = |a||b|* cos θ， the smaller the angle , the bigger the result
            float cos_θ = normalized.dot(mapDirection[i].vector2);
            if (cos_θ > max) {
                max = cos_θ;
                best_match = i;
            }
        }
        return best_match == -1? null:mapDirection[best_match];
    }

    /**
     * check if the ball is collided with a target object
     *
     * @param targetHeight
     * @param targetWidth
     * @param targetPos target position
     * @return
     */
    public static CollisionInfo checkCollision(Ball_2942625 ball, int targetHeight, int targetWidth, Vector2_2942625 targetPos) {

        // get center point circle first
        float centerX = ball.getX() + Ball_2942625.RADIUS;
        float centerY = ball.getY() + Ball_2942625.RADIUS;
        float targetX = targetPos.getX();
        float targetY = targetPos.getY();
        float aabb_halfWidth = targetWidth / 2.f;
        float aabb_halfHeight = targetHeight / 2.f;
        float aabb_centerX = targetX + aabb_halfWidth;
        float aabb_centerY = targetY + aabb_halfHeight;
        float differenceX = centerX - aabb_centerX;
        float differenceY = centerY - aabb_centerY;
        float limitX = clamp(differenceX, -aabb_halfWidth, aabb_halfWidth);
        float limitY = clamp(differenceY, -aabb_halfHeight, aabb_halfHeight);
        float closestX = aabb_centerX + limitX;
        float closestY = aabb_centerY + limitY;
        differenceX = closestX - centerX;
        differenceY = closestY - centerY;
        double sqrt = Math.sqrt(differenceX * differenceX + differenceY * differenceY);
        boolean isCollied = (sqrt < Ball_2942625.RADIUS);

        if (isCollied)
            return new CollisionInfo(true, calculateCollidingDirection(new Vector2_2942625(differenceX, differenceY)), new Vector2_2942625(differenceX, differenceY));
        else
            return CollisionInfo.CollisionFree();
    }

    /**
     * clamp the value between min and max.
     * @param value
     * @param min
     * @param max
     * @return
     */
    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

}
