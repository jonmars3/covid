package pt.isel.poo.model;

public class Level {

    private final int levelNumber, height, width;
    private LevelElement[][] level;

    public Level (int levelNumber, int height, int width) {
        this.levelNumber = levelNumber;
        this.height = height;
        this.width = width;
        level = new LevelElement[height][width];
    }

    public int getNumber() { return levelNumber; }

    //TODO: check if method is working
    public void reset() {
        level = null;
    }

    public void put(int lin, int col, char type) {
        for (int l = 0; l < height; ++l)
            for (int c = 0; c < width; ++c) {
                Position pos = new Position (lin, col);
                switch (type) {
                    case 'X':
                        level[lin][col] = new Wall(pos);
                        break;
                    case '@':
                        level[lin][col] = new Nurse(pos);
                        break;
                    case '*':
                        level[lin][col] = new Virus(pos);
                        break;
                    case 'V':
                        level[lin][col] = new Trash(pos);
                        break;
                    default:
                        level[lin][col] = new Space(pos);
                        break;
                }
            }
    }

    public LevelElement getElement (Position pos) {
        return level[pos.getX()][pos.getY()];
    }
}
