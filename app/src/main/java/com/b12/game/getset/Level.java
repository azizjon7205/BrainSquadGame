package com.b12.game.getset;

public class Level {
    private String levelNumber;
    private int levelStars;
    private boolean status;

    public Level() {
    }

    public Level(String levelNumber, int levelStars, boolean status) {
        this.levelNumber = levelNumber;
        this.levelStars = levelStars;
        this.status = status;
    }

    public String getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(String levelNumber) {
        this.levelNumber = levelNumber;
    }

    public int getLevelStars() {
        return levelStars;
    }

    public void setLevelStars(int levelStars) {
        this.levelStars = levelStars;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
