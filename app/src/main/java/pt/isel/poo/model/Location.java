package pt.isel.poo.model;

public class Location {

    public final int x, y;

    public Location (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location add(Direction direction) {
        //TODO: Prevent the creation of new instances each line add is called (use memoization)
        return new Location (x + direction.dx, y + direction.dy);
    }
}
