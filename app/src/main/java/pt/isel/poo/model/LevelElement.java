package pt.isel.poo.model;

abstract public class LevelElement {

    private Location position;

    public LevelElement (Location position) {
        position = new Location (position.x, position.y);
    }

    public Location getPosition () {
        return position;
    }
}
