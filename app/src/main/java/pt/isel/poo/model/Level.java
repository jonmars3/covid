package pt.isel.poo.model;

import java.io.PrintStream;
import java.util.Scanner;

public class Level {

    //TODO: Add movement listener for all elements (or maybe only mandatory elements - elements that move)
    private Nurse nurse;

    private final int levelNumber;
    public final int width, height;
    private LevelElement[][] level;

    public Level (int levelNumber, int height, int width) {
        this.levelNumber = levelNumber;
        this.height = height;
        this.width = width;
        level = new LevelElement[width][height];
        initNurse();
        nurse.addMovementListener(new Nurse.MovementListener() {
            @Override
            public void nurseHasMoved(Location oldPosition, Location newPosition) {

            }
        })

    }

    private void initNurse() {
        Location nurseInitPosition = new Location (0, 0);
        nurse = new Nurse(nurseInitPosition, this);
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

    public static Level load (Scanner in, int levelNumber) throws Loader.LevelFormatException {
        Loader loader = new Loader(in);
        return loader.load(levelNumber);
    }

    public void save (PrintStream out) {

    }

    public Nurse getNurse () {
        return nurse;
    }

    public void nurseHasMoved (Location oldPosition, Location newPosition) {

    }
}
