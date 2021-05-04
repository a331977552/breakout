package org.game.breakout;

import org.game.utils.GlobalUtils_2942625;

import java.awt.*;

public class PlayerInfoPanel_2942625 {

    private int lives;
    private int currentLevel;
    private int score;

    public void update(long elapsed, int lives, int currentLevel, int score){
        this.lives = lives;
        this.currentLevel = currentLevel;
        this.score = score;
    }

    public void draw(Graphics2D g){
        g.setFont(GlobalUtils_2942625.getGameFont().deriveFont(14.f));
        String live = String.format("lives: %d", lives);
        String level = String.format("level: %d", currentLevel);
        String score = String.format("score: %d", this.score);
        g.setColor(Color.WHITE);
        int liveWith = GlobalUtils_2942625.getStringWidth(g, live);
        int levelWidth = GlobalUtils_2942625.getStringWidth(g, level);
        g.drawString(live, 20, 50);
        g.drawString(level, 45 + liveWith, 50);
        g.drawString(score, 70 + liveWith + levelWidth, 50);
    }

}
