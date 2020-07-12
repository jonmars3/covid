package pt.isel.poo.model;

abstract public class LevelElement {

    protected Location position;
    protected boolean gravityEffect;

    public LevelElement (Location position) {
        this.position = position;
    }

    public Location getPosition () {
        return position;
    }

    public boolean hasGravityEffect() {
        return gravityEffect;
    }
}
