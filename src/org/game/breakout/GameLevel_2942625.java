package org.game.breakout;

import org.game.game2d.TileMap_2942625;
import org.game.utils.GlobalUtils_2942625;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * tileMap with game levels
 */
public class GameLevel_2942625 {
    private int currentLevel;
    private List<TileMap_2942625> levels = new ArrayList<>();

    public int getCurrentLevel() {
        return currentLevel;
    }
    public TileMap_2942625 getCurrentLevelTileMap(){
        return levels.get(currentLevel);
    }


    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void init(){
        TileMap_2942625 tmap0 = new TileMap_2942625("maps", "map-level-0.txt");
        TileMap_2942625 tmap1 = new TileMap_2942625("maps", "map-level-1.txt");
        GlobalUtils_2942625.screenWidth = tmap0.getPixelWidth();
        GlobalUtils_2942625.screenHeight = tmap1.getPixelHeight();
        TileMap_2942625 tmap2 = new TileMap_2942625("maps", "map-level-2.txt");
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
