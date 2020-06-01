package pt.isel.poo.covid.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import pt.isel.poo.covid.tile.Tile;

public class WallTile implements Tile {

    private final Paint brush;

    public WallTile(){
        brush = new Paint();
        brush.setStyle(Paint.Style.FILL_AND_STROKE);

        brush.setColor(Color.BLUE);

    }
    //TODO: Remove when manage to make the images appear.

    @Override
    public void draw(Canvas canvas, int side) {

        canvas.drawRect(4, 4, side-4, side-4, brush);
    }

    @Override
    public boolean setSelect(boolean selected) {
        return false;
    }
}
