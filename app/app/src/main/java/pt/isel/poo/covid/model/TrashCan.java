package pt.isel.poo.covid.model;

import android.content.Context;

import pt.isel.poo.covid.tile.Tile;
import pt.isel.poo.covid.view.HeroTile;
import pt.isel.poo.covid.view.TrashCanTile;

public class TrashCan extends LevelElement {
    /**
     * Initiates a trashcan in a given location
     * @param location the location where the trashcan is placed.
     */
    public TrashCan(Location location){
        super(location);
    }

    public Tile tileType(Context context){
        Tile tile = new TrashCanTile(context);
        return tile;
    }}
