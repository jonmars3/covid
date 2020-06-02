package pt.isel.poo.covid.model;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
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
     *  @param levelNumber the number of the level
     * @param height the height of the level
     * @param width the width of the level
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

    ArrayList<Character> charElements = new ArrayList<Character>();
    /**
     * Resets the bidemensional array.
     */
    public void reset() {

        for (int row = 0; row < arenaHeight; row++) {
            for (int col = 0; col < arenaWidth; col++) {
                elements[row][col] = null;
            }
        }
    }

    /**
     * Places the elements on the bidemensional array
     *
     * @param l    the "row" where the element will be placed.
     * @param c    the "column" where the element will be placed.
     * @param type the char representation of the type of board element that will be placed.
     */
    public void put(int l, int c, char type) {

        charElements.add(type);
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


    public void save(PrintStream output,int savedLevel){
        int i = 0 ;

        output.printf("#%d %d x %d %n" ,savedLevel,arenaHeight,arenaWidth );
        System.out.printf("#%d %d x %d %n",savedLevel,arenaHeight,arenaWidth );
        for (int row = 0; row < arenaHeight; row++) {
        //TODO: FIX THIS
            for (int col = 0 ; col < arenaWidth; col++ , i++) {
                output.print(charElements.get(i));
                System.out.print(charElements.get(i));
                if(i%8 == 0 && i != 0 ){
                    output.println();
                    System.out.println();
                }



            }
        }
    }

}





