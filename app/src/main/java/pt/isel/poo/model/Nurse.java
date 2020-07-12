package pt.isel.poo.model;

/**
 * Represents the controllable nurse (player)
 */
public class Nurse extends LevelElement {

    private Level level;

    public Nurse (Location position, Level level) {
        super(position);
        this.level = level;
        gravityEffect = true;
    }

    private boolean canMove (Direction direction) {
        Location newPosition = new Location (position.x + direction.dx, position.y + direction.dy);
        return (newPosition.x >= 0 && newPosition.x < level.width && newPosition.y >= 0 && newPosition.y < level.height);
    }

    public void move(Direction direction) {
        if (canMove(direction))
            position = position.add(direction);
    }
}