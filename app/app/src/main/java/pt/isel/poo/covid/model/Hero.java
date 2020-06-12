package pt.isel.poo.covid.model;

/**
 * Represents the Hero in the game
 */
public class Hero extends LevelElement {

    private int arenaWidth,arenaHeight;
    private Direction currentDirection;
    private Level level;
    private boolean isDead;

    /**
     * Initiates a Hero placing it at the given location.
     * @param location  the location where the wall is placed.
     */
    public Hero(Location location,Direction initialDirection,int arenaWidth,int arenaHeight,Level level) {
        super(location);
        this.currentDirection = initialDirection;
        this.arenaHeight = arenaHeight;
        this.arenaWidth = arenaWidth;
        this.level = level;
    }


    public boolean canMove(Direction direction){
        final Location newLocation = position.add(direction);
        return newLocation.x >= 0 && newLocation.x < arenaWidth &&
                newLocation.y >= 0 && newLocation.y < arenaHeight &&
                !(level.getElementAt(newLocation.x,newLocation.y) instanceof Wall) &&
                !(level.getElementAt(newLocation.x,newLocation.y) instanceof TrashCan)&&
                !(level.getElementAt(newLocation.x,newLocation.y) instanceof Virus);
    }

    /**
     * Moves the hero in the selected direction
     */
    public void move() {
        if (isDead)
            throw new IllegalStateException();
        if (canMove(currentDirection)) {

            Location oldPosition = position;
            position = position.add(currentDirection);
            level.swap(oldPosition,position);
        }

    }

    public void isDead(Direction direction){
        final Location newLocation = position.add(direction);
        isDead = (level.getElementAt(newLocation.x,newLocation.y) instanceof TrashCan);


    }

    public boolean getDead(){
        return isDead;
    }

    public Direction getHeroDirection (){
        return currentDirection;

    }
    public void changeDirection(Direction newDirection) {
        currentDirection = newDirection;
    }
}