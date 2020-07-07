package pt.isel.poo.covid.model;


import android.content.Context;

import pt.isel.poo.covid.tile.Tile;
import pt.isel.poo.covid.view.VirusTile;

public class Virus extends LevelElement {

    private int arenaWidth,arenaHeight;
    private Direction currentDirection;
    private Level level;
    private boolean isDead;
    private Location oldPosition;

    public Virus(Location location,Direction direction,int arenaWidth,int arenaHeight,Level level) {
        super(location);
        this.currentDirection = direction;
        this.arenaWidth = arenaWidth;
        this.arenaHeight = arenaHeight;
        this.level = level;
        this.character = '*';
        this.oldPosition = location;

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
            oldPosition = position;
            position = position.add(currentDirection);
            level.swap(oldPosition,position);

        }

        if(currentDirection == Direction.SOUTH){
            setDead(currentDirection);
            if(isDead()){
                level.deleteElement(position);

            }

        }
    }

    public void setDead(Direction direction){
        final Location newLocation = position.add(direction);

        if(position.x >= 0 && position.x < arenaWidth &&
                position.y >= 0 && position.y < arenaHeight)
            isDead = (level.getElementAt(newLocation.x,newLocation.y) instanceof TrashCan);

    }

    public boolean isDead(){
        return isDead;
    }

    public void changeDirection(Direction newDirection) {
        currentDirection = newDirection;
    }

    public Tile tileType(Context context){
        Tile tile = new VirusTile(context);
        return tile;
    }

    public Location getOldPosition(){
        return oldPosition;
    }

}

