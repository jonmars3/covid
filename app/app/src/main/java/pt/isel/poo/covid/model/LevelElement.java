package pt.isel.poo.covid.model;

public abstract class LevelElement {

    protected Location position;

    protected LevelElement(Location position){
        this.position = position;
    }

    public Location getPosition(){
        return position;
    }


}
