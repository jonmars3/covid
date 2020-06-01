package pt.isel.poo.covid.model;

import java.util.Scanner;

public  class Level {
    /**
     * Level arena boundaries
     */
    public final int arenaWidth, arenaHeight;
    /**
     *Level arena level
     */
    public int arenaLevel;

    /**
     *
     * @param levelNumber
     * @param height
     * @param width
     */
    public Level(int levelNumber, int height, int width) {

        arenaLevel = levelNumber;
        arenaWidth = width;
        arenaHeight = height;
        elements = new LevelElement[width][height];

    }

    /**
     * Returns the current level
     * @return the current arena level
     */
    public int getNumber() {
        return arenaLevel;
    }


    public static Level setLevel(Scanner in, int currentLevel) throws Loader.LevelFormatException {
        Loader loader = new Loader(in);
        return loader.load(currentLevel);
    }

    private final LevelElement[][]elements;
    /**
     * Resets the bidemensional array.
     */
    public void reset() {
    }

    /**
     * Places the elements on the bidemensional array
     *
     * @param l    the "row" where the element will be placed.
     * @param c    the "column" where the element will be placed.
     * @param type the char representation of the type of board element that will be placed.
     */
    public void put(int l, int c, char type) {

        switch (type) {
            case '@':

                initHero(c, l);
                elements[c][l] = charToLevelElement(type);
                break;

            case 'X':

                initWall(c, l);
                elements[c][l] = charToLevelElement(type);
                break;

            case '*':

                initVirus(c, l);
                elements[c][l] = charToLevelElement(type);
                break;

            case 'V':

                initTrashCan(c, l);
                elements[c][l] = charToLevelElement(type);
                break;
        }
    }

    //TODO: Do this in a better way
    /**
     *
     * @param type char that represents the
     * @return
     */
    private LevelElement charToLevelElement(char type) {
        switch (type) {
            case '@':
                return hero;
            case 'X':
                return wall;
            case '*':
                return virus;
            case 'V':
                return trashCan;
        }
        return null;
    }

    /**
     * The hero instance.
     */
    private Hero hero;

    /**
     * The trashCan instance.
     */
    private TrashCan trashCan;
    /**
     * The virus instance.
     */
    private Virus virus;

    /**
     * The wall instance.
     */
    private Wall wall;

    /**
     *
     * @param c the "column" where the element will be placed.
     * @param l the "row" where the element will be placed.
     */
    private void initVirus(int c, int l) {
        virus = new Virus(new Location(c, l));
    }


    private void initTrashCan(int c, int l) {
        trashCan = new TrashCan(new Location(c, l));
    }

    private void initWall(int c, int l) {
        wall = new Wall(new Location(c, l));
    }

    private void initHero(int c, int l) {
        hero = new Hero(new Location(c, l));
    }

    public LevelElement getElementAt(int c, int l) {
        return elements[c][l];
    }
}

