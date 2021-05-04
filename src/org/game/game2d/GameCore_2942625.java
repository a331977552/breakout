package org.game.game2d;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;


/**
 * @author David Cairns
 * 
 * Core Game class that implements default game loop. Subclasses should
 * implement the draw() method and override the update method.
*/
public abstract class GameCore_2942625 extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;
	protected static final int FONT_SIZE = 12;


    private GameState_2942625 gameState;
    private	long startTime;				// The time the game started
    private long currTime;				// The current time
    private long elapsedTime;			// Elapsed time since previous check
    
    private long frames;				// Used to calculate frames per second (FPS)
    
    private BufferedImage buffer=null;	// buffer is used as a buffered image for drawing offscreen
    private Graphics2D 	  bg=null;    		// The virtual Graphics2D device associated with the above image
    
    
    /**
     * Default constructor for GameCore
     *
     */
    public GameCore_2942625()
    {
        frames = 1;
        startTime = 1;
        currTime = 1;
    }


    public GameState_2942625 getGameState() {
        return gameState;
    }

    public void setGameState(GameState_2942625 gameState) {
        this.gameState = gameState;
    }

    /** 
     * Signals the game loop that it's time to quit 
     * 
     */
    public void stop() { gameState = GameState_2942625.STOP; }


    /** 
     * Starts the game by first initialising the game via init()
     * and then calling the gameLoop()
     *
     * @param full True to set to fullscreen mode, false otherwise
     */
    public void start(boolean full) {
        try
        {
            initInternally(full);
            gameLoop();
        }
        finally 
		{ 
        	
        }
    }


    /**
     * Internal initialisation method.
     *
     * @param full	True to start the game in full screen mode
     */
    private void initInternally(boolean full) {
        addWindowListener(new MWindowAdapter());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(this);
        setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
        init();
    }

    /**
     * subclass overrides this method to init
     */
    public void init() {
    }

    private  class MWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            GameCore_2942625.this.onWindowClosing(e);
        }
    }


    public void onWindowClosing(WindowEvent e){

    }


    /**
     *  Runs through the game loop until stop() is called. 
     *  
     *  This method will call your update() method followed by your draw()
     *  method to display the updated game state. It implements double buffering
     *  for both full screen and windowed mode.
     */
    public void gameLoop() {
        startTime = System.currentTimeMillis();
        currTime = startTime;
        frames = 1;		// Keep a note of frames for performance measure

        Graphics2D g;
        // Create our own buffer
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        bg = (Graphics2D)buffer.createGraphics();
        bg.setClip(0, 0, getWidth(), getHeight());
        g = (Graphics2D)getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        while (gameState != GameState_2942625.STOP) {
            elapsedTime = System.currentTimeMillis() - currTime;
            currTime += elapsedTime;
            // Call the overridden update method
            update(elapsedTime);
	        // Get the current graphics device
	        if (g != null)
	        {
	            	draw(bg);
	            	g.drawImage(buffer,null,0,0);
            }
            frames++;
            // take a nap
            try { Thread.sleep(5); } catch (InterruptedException ex) { }
        }
        System.out.println("game shutdown");
        System.exit(0);
    }
    
    /**
     * @return The current frames per second (FPS)
     */
    public float getFPS()
    {
    	if (currTime - startTime <= 0) return 0.0f;
    	return (float)frames/((currTime - startTime)/1000.0f);
    }

    /**
     * Handles the keyReleased event to check for the 'Escape' key being
     * pressed. If you override this method, make sure you allow the user 
     * to stop the game.
     */
	public void keyReleased(KeyEvent e) 
	{ 
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) stop(); 
	}

	/**
	 * Handler for the keyPressed event (empty)
	 */
	public void keyPressed(KeyEvent e) {

    }
	
	/**
	 * Handler for the keyTyped event (empty)
	 */
	public void keyTyped(KeyEvent e) {	}
		
    /** 
     * Updates the state of the game/animation based on the
     * amount of elapsed time that has passed. You should
     * override this in your game class to do something useful.
     */
    public void update(long elapsedTime) { /* do nothing  */ }


    /** 
     * Subclasses must override this method to draw output to
     * the screen via the Graphics2D object 'g'.
     * 
     * @param g The Graphics2D object to draw with.
     */
    public abstract void draw(Graphics2D g);


}
