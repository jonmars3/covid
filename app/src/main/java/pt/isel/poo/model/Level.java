package pt.isel.poo.model;

import android.content.res.AssetManager;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Level {

    private Nurse nurse;

    private final int levelNumber;
    public final int width, height;
    private LevelElement[][] level;

    public Level (int levelNumber, int height, int width) {
        this.levelNumber = levelNumber;
        this.height = height;
        this.width = width;
        level = new LevelElement[width][height];
    }

    public int getNumber() {
        return levelNumber;
    }

    public void reset() {
        level = null;
    }

    public void put(int l, int c, char type) {
        Location position = new Location (-1, -1);
        if (type != '.')
            position = new Location (c, l);

        switch (type) {
            case 'X':
                level[c][l] = new Wall (position);
                break;
            case '@':
                nurse = new Nurse (position, this);
                level[c][l] = nurse;
                break;
            default:
                level[c][l] = null;
                break;
        }
    }

    public LevelElement getElementAt(int x, int y) {
        return level[x][y];
    }

    public LevelElement getElementAt(Location position) {
        return getElementAt(position.x, position.y);
    }

    public Nurse getNurse() {
        return nurse;
    }

    public Level load (Scanner in, int levelNumber) throws Loader.LevelFormatException {
        Loader loader = new Loader(in);
        loader.load(levelNumber);
        return this;
    }

    public void save (PrintStream out) {

    }
}
