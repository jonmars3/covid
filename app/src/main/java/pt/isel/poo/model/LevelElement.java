package pt.isel.poo.model;

import android.content.Context;

import pt.isel.poo.tile.Tile;

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

    public Tile getTileClass (Context context) {
        return null;
    }
}
