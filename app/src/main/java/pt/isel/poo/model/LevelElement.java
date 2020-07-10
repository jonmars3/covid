package pt.isel.poo.model;

public abstract class LevelElement {

    private Position pos;

    //TODO: solve access dependacy
    public LevelElement (Position pos) {
        this.pos = pos;
    }

    //TODO: check for usability
    public void setPos (Position pos) {
        this.pos.setPos(pos.getX(), pos.getY());
    }
}
