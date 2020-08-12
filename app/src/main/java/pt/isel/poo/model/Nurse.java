package pt.isel.poo.model;

import android.content.Context;

import pt.isel.poo.tile.Tile;
import pt.isel.poo.view.NurseTile;

/**
 * Represents the controllable nurse (player)
 */
public class Nurse extends LevelElement {

    public interface MovementListener {
        void nurseHasMoved (Location oldPosition, Location newPosition);
    }

    //TODO: Implement gravity effect boolean to apply gravity to given element
    private int levelWidth, levelHeigth;

    MovementListener listener;

    public Nurse (Location position, Level level) {
        super(position);
        levelWidth = level.width;
        levelHeigth = level.height;
        gravityEffect = true;
    }


    private boolean canMove (Direction direction) {
        Location newPosition = new Location (position.x + direction.dx, position.y + direction.dy);
        return (newPosition.x >= 0 && newPosition.x < levelWidth && newPosition.y >= 0 && newPosition.y < levelHeigth);
    }

    public void move(Direction direction) {
        if (canMove(direction)) {
            Location oldPosition = position;
            position = position.add(direction);
            listener.nurseHasMoved(oldPosition, position);
        }
    }

    public void addMovementListener (MovementListener listener) {
        this.listener = listener;
    }

    public Tile getTileClass (Context context) {
        return new NurseTile(context, this);
    }
}