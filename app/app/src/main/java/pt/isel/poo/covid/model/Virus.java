package pt.isel.poo.covid.model;


public class Virus extends LevelElement {

    private int arenaWidth,arenaHeight;
    private Direction currentDirection;
    private Level level;
    private boolean isDead;

    public Virus(Location location,Direction direction,int arenaWidth,int arenaHeight,Level level) {
        super(location);
        this.currentDirection = direction;
        this.arenaWidth = arenaWidth;
        this.arenaHeight = arenaHeight;
        this.level = level;

    }

    public boolean canMove(Direction direction){
        final Location newLocation = position.add(direction);
        return newLocation.x >= 0 && newLocation.x < arenaWidth &&
                newLocation.y >= 0 && newLocation.y < arenaHeight &&
                !(level.getElementAt(newLocation.x,newLocation.y) instanceof Wall) &&
                !(level.getElementAt(newLocation.x,newLocation.y) instanceof Hero) &&
                !(level.getElementAt(newLocation.x,newLocation.y) instanceof TrashCan) &&
                !(level.getElementAt(newLocation.x,newLocation.y) instanceof Virus);

    }

    /**
     * Moves the virus in the selected direction
     */
    public void move() {

        if (canMove(currentDirection)) {

            Location oldPosition = position;
            position = position.add(currentDirection);
            level.swap(oldPosition,position);
        }

        if(isDead){
            level.deleteElement(position);

        }

    }

    public boolean isDead(Direction direction){
        final Location newLocation = position.add(direction);
        if(newLocation.x >= 0 && newLocation.x < arenaWidth &&
                newLocation.y >= 0 && newLocation.y < arenaHeight)
            isDead = (level.getElementAt(newLocation.x,newLocation.y) instanceof TrashCan);
        return isDead;
    }

    public void changeDirection(Direction newDirection) {
        currentDirection = newDirection;
    }
}

