package org.game.breakout;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;


import org.game.game2d.*;
import org.game.game2d.particle.ParticleGenerator_2942625;
import org.game.game2d.sound.filters.EchoFilterAbstract_2942625;
import org.game.game2d.sound.filters.FadeFilterAbstract_2942625;
import org.game.game2d.sound.SoundManager_2942625;
import org.game.utils.Env_2942625;
import org.game.utils.GlobalUtils_2942625;

import static org.game.utils.GlobalUtils_2942625.screenHeight;
import static org.game.utils.GlobalUtils_2942625.screenWidth;

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

public class BreakoutGame_2942625 extends GameCore_2942625 {
    // Useful game constants
    private ParticleGenerator_2942625 particleGenerator;
    private Set<Integer> keys = new HashSet<>();
    private Set<Integer> keyPressed = new HashSet<>();
    private int lives = 3;//by default, player has 3 lives
    private PlayerInfoPanel_2942625 playerInfoPanel =new PlayerInfoPanel_2942625();
    private GameLevel_2942625 gameLevel = new GameLevel_2942625();
    Sprite_2942625 player = null;
    Sprite_2942625 ball = null;
    private Color backgroundColor = new Color(17,26,51);
    private EchoFilterAbstract_2942625 echoFilter = new EchoFilterAbstract_2942625(22050, .5f);//echo filter for hitting a brick
    private FadeFilterAbstract_2942625 fadeFilter = new FadeFilterAbstract_2942625();//echo filter for hitting a brick
    ArrayList<Sprite_2942625> clouds = new ArrayList<>();


    private int score = 0;// The score will be how many bricks the ball hit
    private Font gameFont;




    /**
     * Initialise the class, e.g. set up variables, load images,
     * create animations, register event handlers
     */
    @Override
    public void init() {
        this.gameFont = GlobalUtils_2942625.getGameFont();
        particleGenerator = new ParticleGenerator_2942625("images/particle.png");
        setGameState(GameState_2942625.MENU);
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
        player = new Paddle_2942625();
        ball = new Ball_2942625();
    }

    private void loadingSounds() {
        SoundManager_2942625.getInstance().play("sounds/background.wav", true,null);
    }


    /**
     * initialize game objects such as position, velocity etc.
     */
    public void resetGameObjects() {
        player.setX((screenWidth - Paddle_2942625.WIDTH) / 2);
        player.setY(screenHeight - Paddle_2942625.HEIGHT);
        ((Ball_2942625) ball).resetBall(
                player.getX() + Paddle_2942625.WIDTH / 2 - Ball_2942625.RADIUS,
                player.getY() - Ball_2942625.RADIUS * 2,
                   Ball_2942625.INIT_VELOCITY_X,
                   Ball_2942625.INIT_VELOCITY_Y
                );

        player.setVelocityX(Paddle_2942625.VELOCITY_X);
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
        for (Sprite_2942625 s : clouds) {
            s.draw(g);
        }
        if (this.getGameState() == GameState_2942625.MENU) {
            drawMenu(g);
        } else {
            gameLevel.draw(g);
            player.draw(g);
            ball.draw(g);
            playerInfoPanel.draw(g);
            particleGenerator.draw(g);
            if (this.getGameState() == GameState_2942625.WIN) {
                drawWinMenu(g);
            } else if (this.getGameState() == GameState_2942625.LOSE) {
                drawLoseMenu(g);
            }
        }
        if (Env_2942625.LOG_LEVEL == Env_2942625.DEBUG) {
            String msg = String.format("window width %s height %s", screenWidth, screenHeight);
            g.setColor(Color.white);
            g.setFont(this.gameFont.deriveFont(12.f));
            int stringWidth = GlobalUtils_2942625.getStringWidth(g, msg);
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
        int width1 = GlobalUtils_2942625.getStringWidth(g, "You LOST :( Press Enter to Menu");
        g.drawString("You LOST :( Press Enter to Menu", (getWidth() - width1) / 2, 300);
    }

    /**
     * draw winning menu when all bricks are destroyed.
     * @param g
     */
    private void drawWinMenu(Graphics2D g) {
        g.setFont(this.gameFont.deriveFont(18.f));
        g.setColor(Color.white);
        int width1 = GlobalUtils_2942625.getStringWidth(g, "you WIN :) Press Enter to Next LEVEL");
        g.drawString("you WIN :) Press Enter to Next LEVEL", (getWidth() - width1) / 2, 300);
    }

    /**
     * draw game initial menu
     * @param g
     */
    private void drawMenu(Graphics2D g) {
        g.setFont(this.gameFont.deriveFont(18.f));
        g.setColor(Color.white);
        int width1 = GlobalUtils_2942625.getStringWidth(g, "Press Enter to start the game");
        int width2 = GlobalUtils_2942625.getStringWidth(g, "Press UP and DOWN to change current level");
        String currentLevelStr = String.format("current Level: %d", gameLevel.getCurrentLevel());
        int width3 = GlobalUtils_2942625.getStringWidth(g, currentLevelStr);
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
        for (Sprite_2942625 s : clouds)
            s.update(elapsed);
        // Now update the sprites animation and position
        player.update(elapsed);
        ball.update(elapsed);
        playerInfoPanel.update(elapsed,this.lives, gameLevel.getCurrentLevel(), this.score);
        // Then check for any collisions that may have occurred
        checkBrickCollision((Ball_2942625) ball, gameLevel.getCurrentLevelTileMap());
        checkPaddleCollision((Paddle_2942625) player, (Ball_2942625) ball);
        processInput(elapsed);
        checkIfBallOutOfBoundary();
        particleGenerator.update(elapsed, ball, new Vector2_2942625(13,13));
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
        this.setGameState(GameState_2942625.LOSE);
    }

    /**
     * reset game, tilemap.
     */
    private void resetGame() {
        this.lives = 3;
        this.score = 0;
        gameLevel.resetGame();
        this.setGameState(GameState_2942625.MENU);
    }

    @Override
    public void onWindowClosing(WindowEvent e) {

    }


    private void processInput(long elapsed) {
        if (this.getGameState() == GameState_2942625.RUNNING) {
            processRunningInput(elapsed);
        } else if (this.getGameState() == GameState_2942625.MENU) {
            processMenuInput();
        } else if (this.getGameState() == GameState_2942625.WIN) {
            processWinInput();
        } else if (this.getGameState() == GameState_2942625.LOSE) {
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
            this.setGameState(GameState_2942625.RUNNING);
        }
    }

    /**
     * change level and start game on menu UI
     */
    private void processMenuInput() {
        if (keys.contains(KeyEvent.VK_ENTER) && !keyPressed.contains(KeyEvent.VK_ENTER)) {
            keyPressed.add(KeyEvent.VK_ENTER);
            this.setGameState(GameState_2942625.RUNNING);
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
                boolean ballLaunched = ((Ball_2942625) ball).isLaunched();
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
                boolean ballLaunched = ((Ball_2942625) ball).isLaunched();
                if (!ballLaunched) {
                    ball.setX(ball.getX() + offsetX);
                }
            }
        }
        if (keys.contains(KeyEvent.VK_SPACE)) {
            ((Ball_2942625) ball).setLaunched(true);
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

    public void checkBrickCollision(Ball_2942625 ball, TileMap_2942625 tmap) {
        List<Tile_2942625> tiles = tmap.getTiles();
        int tileHeight = tmap.getTileHeight();
        int tileWidth = tmap.getTileWidth();

        for (Tile_2942625 tile : tiles) {
            if (!tile.isDestroy()) {
                Collision_2942625.CollisionInfo collisionInfo = Collision_2942625.checkCollision(ball, tileHeight, tileWidth, tile.getPosition());
                if (collisionInfo.isCollided()) {
                    if (!tile.isSolid()) {
                        this.score++;
                        SoundManager_2942625.getInstance().play("sounds/hit_destroyable_brick.wav",false,echoFilter);
                        tile.setDestroy(true);
                        boolean allDestroyed = tiles.stream().allMatch(t -> t.isDestroy() || t.isSolid());
                        if (allDestroyed) {
                            this.setGameState(GameState_2942625.WIN);
                            resetGameObjects();
                        }
                    } else {
                        SoundManager_2942625.getInstance().play("sounds/hit_solid_brick.wav",false,fadeFilter);
                    }
                    Vector2_2942625 difference = collisionInfo.getDifference();
                    Collision_2942625.Direction direction = collisionInfo.getDirection();
                    if (direction == Collision_2942625.Direction.UP || direction == Collision_2942625.Direction.DOWN) {
                        this.ball.setVelocityY(-this.ball.getVelocityY());//bounce back vertically.
                        float diffValue = Ball_2942625.RADIUS - Math.abs(difference.getY());//how far the ball has gone through
                        this.ball.setY(this.ball.getY() + (direction == Collision_2942625.Direction.UP ? -diffValue : diffValue));//correct the ball's position
                    } else if (direction == Collision_2942625.Direction.LEFT || direction == Collision_2942625.Direction.RIGHT)//reverse horizontally
                    {
                        this.ball.setVelocityX(-this.ball.getVelocityX());//bounce back horizontally.
                        float diffValue = Ball_2942625.RADIUS - Math.abs(difference.getX());//how far the ball has gone through
                        this.ball.setX(this.ball.getX() + (direction == Collision_2942625.Direction.LEFT ? diffValue : -diffValue));//correct the ball's position
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
    private void checkPaddleCollision(Paddle_2942625 paddle, Ball_2942625 ball) {
        if (ball.isLaunched() && ball.getY() > 530) {
            int height = Paddle_2942625.HEIGHT;
            int width = paddle.WIDTH;
            Collision_2942625.CollisionInfo collisionInfo = Collision_2942625.checkCollision(ball, height, width, paddle.getPosition());
            if (collisionInfo.isCollided()) {
                SoundManager_2942625.getInstance().play("sounds/bounce_back.wav");
                float paddleXCenter = paddle.getX() + width / 2.f;
                float ballXCenter = ball.getX() + Ball_2942625.RADIUS;
                float xDistance = ballXCenter - paddleXCenter;
                float percent = xDistance / (width / 2.f);
                Vector2_2942625 preVelocity = ball.getVelocity();
                //change horizontal velocity based on how far the ball is collided from the center of the paddle
                ball.setVelocityX(Ball_2942625.INIT_VELOCITY_X * percent * Ball_2942625.COLLISION_STRENGTH);
                Vector2_2942625 newVelocityDirection = new Vector2_2942625(ball.getVelocityX(), ball.getVelocityY()).normalize();
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
