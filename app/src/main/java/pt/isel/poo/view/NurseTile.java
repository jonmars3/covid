package pt.isel.poo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import pt.isel.poo.covid.R;
import pt.isel.poo.model.Direction;
import pt.isel.poo.model.Nurse;
import pt.isel.poo.tile.Img;
import pt.isel.poo.tile.Tile;

public class NurseTile implements Tile {

    private final Paint brush;
    private final Img image;
    private final Nurse model;

    public NurseTile(Context context, Nurse nurse) {
        brush = new Paint();
        brush.setStyle(Paint.Style.FILL_AND_STROKE);
        image = new Img(context, R.drawable.nurse);
        model = nurse;
    }

    //TODO: implement facing rotation
    @Override
    public void draw (Canvas canvas, int side) {
        image.draw(canvas, side, side, brush);
    }

    @Override
    public boolean setSelect (boolean selected) {
        return false;
    }
}
