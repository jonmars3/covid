package pt.isel.poo.model;

import android.content.Context;

import pt.isel.poo.tile.Tile;
import pt.isel.poo.view.VirusTile;

public class Virus extends LevelElement{

    public Virus(Location position) {
        super(position);
        gravityEffect = true;
    }

    public Tile getTileClass (Context context) {
        return new VirusTile(context);
    }
}
