package org.game.breakout;

import org.game.game2d.TileMap;
import org.game.utils.GlobalUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * tileMap with game levels
 */
public class GameLevel {
    private int currentLevel;
    private List<TileMap> levels = new ArrayList<>();

    public int getCurrentLevel() {
        return currentLevel;
    }
    public TileMap getCurrentLevelTileMap(){
        return levels.get(currentLevel);
    }


    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void init(){
        TileMap tmap0 = new TileMap("maps", "map-level-0.txt");
        TileMap tmap1 = new TileMap("maps", "map-level-1.txt");
        GlobalUtils.screenWidth = tmap0.getPixelWidth();
        GlobalUtils.screenHeight = tmap1.getPixelHeight();
        TileMap tmap2 = new TileMap("maps", "map-level-2.txt");
        levels.add(tmap0);
        levels.add(tmap1);
        levels.add(tmap2);
        levels.stream().forEach(tileMap -> {
            System.out.println(tileMap.getMapfile()+":  ");
            System.out.println(tileMap.toString());
            System.out.println();

        });
    }
    public int increaseLevel() {
        this.currentLevel++;
        this.currentLevel = this.currentLevel % levels.size();
        return currentLevel;
    }

    public int decreaseLevel() {
        this.currentLevel--;
        if (this.currentLevel < 0) {
            this.currentLevel = levels.size() - 1;
        }
        return currentLevel;
    }

    public void resetGame() {
        this.setCurrentLevel(0);
        this.levels.stream().forEach(map->{
            map.resetTiles();
        });
    }

    public void draw(Graphics2D g) {
        getCurrentLevelTileMap().draw(g);
    }
}
