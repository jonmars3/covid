package pt.isel.poo.model;

public class Level {

    private final int levelNumber, height, width;
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
        Location position = new Location (c, l);
        switch (type) {
            case 'X':
                level[c][l] = new Wall (position);
                break;
            case '@':
                level[c][l] = new Nurse (position);
                break;
            default:
                level[c][l] = null;
                break;
        }
    }
}
