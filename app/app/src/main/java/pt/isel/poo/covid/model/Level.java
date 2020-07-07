package pt.isel.poo.covid.model;

import java.io.PrintStream;
import java.util.ArrayList;

public  class Level {


    public final int arenaWidth, arenaHeight;
    public int arenaLevel;
    private final LevelElement[][]elements;     //Array that contains the elements.
    ArrayList<Virus> virusList = new ArrayList<Virus>(); //Array that contains the virus present in the current level


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

    /**
     * Resets the bi-dimensional array.
     */
    //TODO: Created due to being used in a method of the Loader class(which wasn't used), See what do with it.
    public void reset() {

        for (int row = 0; row < arenaHeight; row++) {
            for (int col = 0; col < arenaWidth; col++) {
                elements[row][col] = null;
            }
        }
    }

    /**
     * Places the elements on the bi-dimensional array
     *
     * @param l    the "row" where the element will be placed.
     * @param c    the "column" where the element will be placed.
     * @param type the char representation of the type of board element that will be placed.
     */
    public void put(int l, int c, char type) {

        switch (type) {
            case '@':

                initHero(c, l);
                elements[c][l] = hero;
                break;

            case 'X':

                initWall(c, l);
                elements[c][l] = wall;
                break;

            case '*':

                initVirus(c, l);
                elements[c][l] = virus;

                break;

            case 'V':

                initTrashCan(c, l);
                elements[c][l] = trashCan;
                break;

            default:
                elements[c][l] = null;
        }
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
        virus = new Virus(new Location(c, l),Direction.NONE,arenaWidth,arenaHeight,this);
        virusList.add(virus);
    }

    private void initTrashCan(int c, int l) {
        trashCan = new TrashCan(new Location(c, l));
    }

    private void initWall(int c, int l) {
        wall = new Wall(new Location(c, l));
    }

    private void initHero(int c, int l) {
        hero = new Hero(new Location(c, l),Direction.NONE,arenaWidth,arenaHeight,this);
    }

    public Hero getHero(){
        return hero;
    }

    public ArrayList getVirusList (){
        return virusList;
    }
    public int getVirusCount(){
        return virusList.size();
    }

    public LevelElement getElementAt(int c, int l) {
        return elements[c][l];
    }

    public void swap (Location oldLocation,Location newLocation){

        elements[newLocation.x][newLocation.y] = elements[oldLocation.x][oldLocation.y] ;
        elements[oldLocation.x][oldLocation.y] = null;

    }

    public void deleteElement(Location oldLocation){
        elements[oldLocation.x][oldLocation.y] = null;
    }


    public void save(PrintStream output,int savedLevel){
        int i = 0 ;

        output.printf("#%d %d x %d %n" ,savedLevel,arenaHeight,arenaWidth );
        for (int l = 0; l < arenaHeight; l++) {

            for (int c = 0 ; c < arenaWidth; c++) {
                if(getElementAt(c,l)!=null) output.print(getElementAt(c,l).getChar());
                else output.print('.');
                if(c%8 ==0 && c != 0 )output.println();
            }
        }
    }
}
