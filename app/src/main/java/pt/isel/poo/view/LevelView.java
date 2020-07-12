package pt.isel.poo.view;

import android.widget.Toast;

import pt.isel.poo.MainActivity;
import pt.isel.poo.model.Level;
import pt.isel.poo.model.Location;
import pt.isel.poo.model.Nurse;
import pt.isel.poo.tile.Tile;
import pt.isel.poo.tile.TilePanel;

public class LevelView {

    private final TilePanel panel;
    private final Level level;
    private final Nurse nurse;

    public LevelView (TilePanel panel, Level level, Nurse nurse) {
        this.panel = panel;
        this.level = level;
        this.nurse = nurse;
        panel.setTile(nurse.getPosition().x, nurse.getPosition().y, new NurseTile(panel.getContext(), nurse));
    }

    public void updatePosition (Location prevLocation) {
        final Tile tile = panel.getTile(prevLocation.x, prevLocation.y);
        panel.setTile(prevLocation.x, prevLocation.y, null);
        panel.setTile(nurse.getPosition().x, nurse.getPosition().y, tile);
    }

    public void init() {
        for (int y = 0; y < level.height; ++y)
            for (int x = 0; x < level.width; ++x) {
                //updatePosition(new Location(x,y));
            }
    }

    //TODO: check if correct context
    public void printText (int text) {
        Toast.makeText(panel.getContext(), text, Toast.LENGTH_LONG).show();
    }
}
