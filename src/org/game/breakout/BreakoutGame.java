package org.game.breakout;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;


import org.game.game2d.*;
import org.game.game2d.particle.ParticleGenerator;
import org.game.game2d.sound.filters.EchoFilterAbstract;
import org.game.game2d.sound.filters.FadeFilterAbstract;
import org.game.game2d.sound.SoundManager;
import org.game.utils.Env;
import org.game.utils.GlobalUtils;

import static org.game.utils.GlobalUtils.screenHeight;
import static org.game.utils.GlobalUtils.screenWidth;

// Game demonstrates how we can override the GameCore class
// to create our own 'game'. We usually need to implement at
// least 'draw' and 'update' (not including any local event handling)
// to begin the process. You should also add code to the 'init'
// method that will initialise event handlers etc. By default GameCore
// will handle the 'Escape' key to quit the game but you should
// override this with your own event handler.

/**
 * @author David Cairns
 */
@SuppressWarnings("serial")

public class BreakoutGame extends GameCore {
    // Useful game constants
    private ParticleGenerator particleGenerator;
    private Set<Integer> keys = new HashSet<>();
    private Set<Integer> keyPressed = new HashSet<>();
    private int lives = 3;//by default, player has 3 lives
    private PlayerInfoPanel playerInfoPanel =new PlayerInfoPanel();
    private GameLevel gameLevel = new GameLevel();
    Sprite player = null;
    Sprite ball = null;
    private Color backgroundColor = new Color(17,26,51);
    private EchoFilterAbstract echoFilter = new EchoFilterAbstract(22050, .5f);//echo filter for hitting a brick
    private FadeFilterAbstract fadeFilter = new FadeFilterAbstract();//echo filter for hitting a brick
    ArrayList<Sprite> clouds = new ArrayList<>();


    private int score = 0;// The score will be how many bricks the ball hit
    private Font gameFont;




    /**
     * Initialise the class, e.g. set up variables, load images,
     * create animations, register event handlers
     */
    @Override
    public void init() {
        this.gameFont = GlobalUtils.getGameFont();
        particleGenerator = new ParticleGenerator("images/particle.png");
        setGameState(GameState.MENU);
        gameLevel.init();
        loadingSounds();
        setSize(screenWidth, screenHeight);
        loadingGameObjs();
        resetGameObjects();
    }

    /**
     * loading player, ball, bricks, and backgrounds etc.
     */
    private void loadingGameObjs() {
        player = new Paddle();
        ball = new Ball();
    }

    private void loadingSounds() {
        SoundManager.getInstance().play("sounds/background.wav", true,null);
    }


    /**
     * initialize game objects such as position, velocity etc.
     */
    public void resetGameObjects() {
        player.setX((screenWidth - Paddle.WIDTH) / 2);
        player.setY(screenHeight - Paddle.HEIGHT);
        ((Ball) ball).resetBall(
                player.getX() + Paddle.WIDTH / 2 - Ball.RADIUS,
                player.getY() - Ball.RADIUS * 2,
                   Ball.INIT_VELOCITY_X,
                   Ball.INIT_VELOCITY_Y
                );

        player.setVelocityX(Paddle.VELOCITY_X);
        player.setVelocityY(0);
        player.show();
        ball.show();
    }

    /**
     * Draw the current state of the game
     */
    public void draw(Graphics2D g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        // Apply offsets to sprites then draw them
        for (Sprite s : clouds) {
            s.draw(g);
        }
        if (this.getGameState() == GameState.MENU) {
            drawMenu(g);
        } else {
            gameLevel.draw(g);
            player.draw(g);
            ball.draw(g);
            playerInfoPanel.draw(g);
            particleGenerator.draw(g);
            if (this.getGameState() == GameState.WIN) {
                drawWinMenu(g);
            } else if (this.getGameState() == GameState.LOSE) {
                drawLoseMenu(g);
            }
        }
        if (Env.LOG_LEVEL == Env.DEBUG) {
            String msg = String.format("window width %s height %s", screenWidth, screenHeight);
            g.setColor(Color.white);
            g.setFont(this.gameFont.deriveFont(12.f));
            int stringWidth = GlobalUtils.getStringWidth(g, msg);
            g.drawString(msg, getWidth() - stringWidth - 20, 50);
        }
        float fps = getFPS();
        g.drawString(String.format("FPS:%.2f", fps), 20, 70);
    }

    /**
     * draw losing menu when  lives = 0
     * @param g
     */
    private void drawLoseMenu(Graphics2D g) {
        g.setFont(this.gameFont.deriveFont(18.f));
        g.setColor(Color.RED);
        int width1 = GlobalUtils.getStringWidth(g, "You LOST :( Press Enter to Menu");
        g.drawString("You LOST :( Press Enter to Menu", (getWidth() - width1) / 2, 300);
    }

    /**
     * draw winning menu when all bricks are destroyed.
     * @param g
     */
    private void drawWinMenu(Graphics2D g) {
        g.setFont(this.gameFont.deriveFont(18.f));
        g.setColor(Color.white);
        int width1 = GlobalUtils.getStringWidth(g, "you WIN :) Press Enter to Next LEVEL");
        g.drawString("you WIN :) Press Enter to Next LEVEL", (getWidth() - width1) / 2, 300);
    }

    /**
     * draw game initial menu
     * @param g
     */
    private void drawMenu(Graphics2D g) {
        g.setFont(this.gameFont.deriveFont(18.f));
        g.setColor(Color.white);
        int width1 = GlobalUtils.getStringWidth(g, "Press Enter to start the game");
        int width2 = GlobalUtils.getStringWidth(g, "Press UP and DOWN to change current level");
        String currentLevelStr = String.format("current Level: %d", gameLevel.getCurrentLevel());
        int width3 = GlobalUtils.getStringWidth(g, currentLevelStr);
        g.drawString("Press Enter to start the game", (getWidth() - width1) / 2, 300);
        g.drawString("Press UP and DOWN to change current level", (getWidth() - width2) / 2, 330);
        g.drawString(currentLevelStr, (getWidth() - width3) / 2, 360);
    }



    /**
     * Update any sprites and check for collisions
     *
     * @param elapsed The elapsed time between this call and the previous call of elapsed
     */
    public void update(long elapsed) {
//        System.out.println(elapsed);
        for (Sprite s : clouds)
            s.update(elapsed);
        // Now update the sprites animation and position
        player.update(elapsed);
        ball.update(elapsed);
        playerInfoPanel.update(elapsed,this.lives, gameLevel.getCurrentLevel(), this.score);
        // Then check for any collisions that may have occurred
        checkBrickCollision((Ball) ball, gameLevel.getCurrentLevelTileMap());
        checkPaddleCollision((Paddle) player, (Ball) ball);
        processInput(elapsed);
        checkIfBallOutOfBoundary();
        particleGenerator.update(elapsed, ball, new Vector2(13,13));
    }

    /**
     * check if the ball drop out of bottom boundary.
     * in which case, lives will be subtract by 1.
     */
    private void checkIfBallOutOfBoundary() {
        if (ball.getPosition().getY() > screenHeight) {
            this.lives--;
            if (this.lives == 0) {
                gameLost();
            }
            resetGameObjects();
        }
    }

    private void gameLost() {
        this.setGameState(GameState.LOSE);
    }

    /**
     * reset game, tilemap.
     */
    private void resetGame() {
        this.lives = 3;
        this.score = 0;
        gameLevel.resetGame();
        this.setGameState(GameState.MENU);
    }

    @Override
    public void onWindowClosing(WindowEvent e) {

    }


    private void processInput(long elapsed) {
        if (this.getGameState() == GameState.RUNNING) {
            processRunningInput(elapsed);
        } else if (this.getGameState() == GameState.MENU) {
            processMenuInput();
        } else if (this.getGameState() == GameState.WIN) {
            processWinInput();
        } else if (this.getGameState() == GameState.LOSE) {
            processLoseInput();
        }
    }

    private void processLoseInput() {
        if (keys.contains(KeyEvent.VK_ENTER) && !keyPressed.contains(KeyEvent.VK_ENTER)) {
            resetGame();
            resetGameObjects();
        }
    }

    private void processWinInput() {
        if (keys.contains(KeyEvent.VK_ENTER) && !keyPressed.contains(KeyEvent.VK_ENTER)) {
            gameLevel.increaseLevel();
            this.setGameState(GameState.RUNNING);
        }
    }

    /**
     * change level and start game on menu UI
     */
    private void processMenuInput() {
        if (keys.contains(KeyEvent.VK_ENTER) && !keyPressed.contains(KeyEvent.VK_ENTER)) {
            keyPressed.add(KeyEvent.VK_ENTER);
            this.setGameState(GameState.RUNNING);
        } else if (keys.contains(KeyEvent.VK_UP) && !keyPressed.contains(KeyEvent.VK_UP)) {
            gameLevel.increaseLevel();
            keyPressed.add(KeyEvent.VK_UP);
        } else if (keys.contains(KeyEvent.VK_DOWN) && !keyPressed.contains(KeyEvent.VK_DOWN)) {
            gameLevel.decreaseLevel();
            keyPressed.add(KeyEvent.VK_DOWN);
        }
    }




    /**
     * process users operation when game is actually started
     *
     * @param elapsed
     */
    private void processRunningInput(long elapsed) {
        if (keys.contains(KeyEvent.VK_A)) {
            if (player.getX() >= 0) {
                float offsetX = player.getVelocityX() * elapsed;
                player.setX(player.getX() - offsetX);
                boolean ballLaunched = ((Ball) ball).isLaunched();
                if (!ballLaunched) {
                    ball.setX(ball.getX() - offsetX);
                }
            }
        }
        if (keys.contains(KeyEvent.VK_D)) {
            //if the player is not going out of right edge of screen
            if (player.getX() <= (screenWidth - player.getWidth())) {
                float offsetX = player.getVelocityX() * elapsed;
                player.setX(player.getX() + offsetX);
                boolean ballLaunched = ((Ball) ball).isLaunched();
                if (!ballLaunched) {
                    ball.setX(ball.getX() + offsetX);
                }
            }
        }
        if (keys.contains(KeyEvent.VK_SPACE)) {
            ((Ball) ball).setLaunched(true);
        }
    }


    /**
     * Override of the keyPressed event defined in GameCore to catch our
     * own events
     *
     * @param e The event that has been generated
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        keys.add(key);
        if (key == KeyEvent.VK_ESCAPE) stop();
    }


    /**
     * check collision between ball and bricks.
     * different type of bricks will respond to the collision differently.
     *
     * @param ball The Sprite to check collisions for
     * @param tmap The tile map to check
     */

    public void checkBrickCollision(Ball ball, TileMap tmap) {
        List<Tile> tiles = tmap.getTiles();
        int tileHeight = tmap.getTileHeight();
        int tileWidth = tmap.getTileWidth();

        for (Tile tile : tiles) {
            if (!tile.isDestroy()) {
                Collision.CollisionInfo collisionInfo = Collision.checkCollision(ball, tileHeight, tileWidth, tile.getPosition());
                if (collisionInfo.isCollided()) {
                    if (!tile.isSolid()) {
                        this.score++;
                        SoundManager.getInstance().play("sounds/hit_destroyable_brick.wav",false,echoFilter);
                        tile.setDestroy(true);
                        boolean allDestroyed = tiles.stream().allMatch(t -> t.isDestroy() || t.isSolid());
                        if (allDestroyed) {
                            this.setGameState(GameState.WIN);
                            resetGameObjects();
                        }
                    } else {
                        SoundManager.getInstance().play("sounds/hit_solid_brick.wav",false,fadeFilter);
                    }
                    Vector2 difference = collisionInfo.getDifference();
                    Collision.Direction direction = collisionInfo.getDirection();
                    if (direction == Collision.Direction.UP || direction == Collision.Direction.DOWN) {
                        this.ball.setVelocityY(-this.ball.getVelocityY());//bounce back vertically.
                        float diffValue = Ball.RADIUS - Math.abs(difference.getY());//how far the ball has gone through
                        this.ball.setY(this.ball.getY() + (direction == Collision.Direction.UP ? -diffValue : diffValue));//correct the ball's position
                    } else if (direction == Collision.Direction.LEFT || direction == Collision.Direction.RIGHT)//reverse horizontally
                    {
                        this.ball.setVelocityX(-this.ball.getVelocityX());//bounce back horizontally.
                        float diffValue = Ball.RADIUS - Math.abs(difference.getX());//how far the ball has gone through
                        this.ball.setX(this.ball.getX() + (direction == Collision.Direction.LEFT ? diffValue : -diffValue));//correct the ball's position
                    }
                }
            }
        }
    }

    /**
     * check collision between paddle and ball
     * @param paddle
     * @param ball
     */
    private void checkPaddleCollision(Paddle paddle, Ball ball) {
        if (ball.isLaunched() && ball.getY() > 530) {
            int height = Paddle.HEIGHT;
            int width = paddle.WIDTH;
            Collision.CollisionInfo collisionInfo = Collision.checkCollision(ball, height, width, paddle.getPosition());
            if (collisionInfo.isCollided()) {
                SoundManager.getInstance().play("sounds/bounce_back.wav");
                float paddleXCenter = paddle.getX() + width / 2.f;
                float ballXCenter = ball.getX() + Ball.RADIUS;
                float xDistance = ballXCenter - paddleXCenter;
                float percent = xDistance / (width / 2.f);
                Vector2 preVelocity = ball.getVelocity();
                //change horizontal velocity based on how far the ball is collided from the center of the paddle
                ball.setVelocityX(Ball.INIT_VELOCITY_X * percent * Ball.COLLISION_STRENGTH);
                Vector2 newVelocityDirection = new Vector2(ball.getVelocityX(), ball.getVelocityY()).normalize();
                float strengthOfPreVelocity = preVelocity.length();
                //normalized direction multiplies strength of previous velocity length to keep consistence.
                ball.setVelocity(newVelocityDirection.multiply(strengthOfPreVelocity));
                //given negative velocity regardless previous direction of velocity
                ball.setVelocityY(-1.f * Math.abs(ball.getVelocityY()));
            }
        }
    }


    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();
        keys.remove(key);
        keyPressed.remove(key);
        // Switch statement instead of lots of ifs...
        // Need to use break to prevent fall through.
        switch (key) {
            case KeyEvent.VK_ESCAPE:
                stop();
                break;
            default:
                break;
        }
    }
}
