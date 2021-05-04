package org.game.game2d;

/**
 * A Tile in the TileMap.
 * 
 * @author David Cairns
 *
 */
public class Tile_2942625 {
	public static final char SOLID = 's';
	public static final char EMPTY = '.';

	private char character=' ';	// The character associated with this tile
	private int xc=0;			// The tile's x coordinate in pixels
	private int yc=0;			// The tile's y coordinate in pixels
	private Vector2_2942625 pos;
	private final boolean solid; //is it destroyable ?
	private boolean destroy = false;    // is it destroyed ?



	/**
	 * Create an instance of a tile
	 * @param c	The character associated with this tile
	 * @param x The x tile coordinate in pixels
	 * @param y The y tile coordinate in pixels
	 */
	public Tile_2942625(char c, int x, int y, boolean solid)
	{
		this.solid =solid;
		character = c;
		xc = x;
		yc = y;
		pos = new Vector2_2942625(xc,yc);
	}

	public Vector2_2942625 getPosition(){
		return pos;
	}
	public boolean isSolid() {
		return solid;
	}

	public boolean isDestroy() {
		return destroy;
	}

	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}

	/**
	 * @return The character for this tile
	 */
	public char getCharacter() {
		return character;
	}

	/**
	 * @param character The character to set the tile to
	 */
	public void setCharacter(char character) {
		this.character = character;
	}

	/**
	 * @return The x coordinate (in pixels)
	 */
	public int getXC() {
		return xc;
	}

	/**
	 * @return The y coordinate (in pixels)
	 */
	public int getYC() {
		return yc;
	}
}
