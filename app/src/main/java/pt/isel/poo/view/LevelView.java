package pt.isel.poo.view;

import android.widget.Toast;

import pt.isel.poo.MainActivity;
import pt.isel.poo.model.Level;
import pt.isel.poo.model.LevelElement;
import pt.isel.poo.model.Location;
import pt.isel.poo.model.Nurse;
import pt.isel.poo.tile.Tile;
import pt.isel.poo.tile.TilePanel;

public class LevelView {

    private final TilePanel panel;
    private final Level level;

    public LevelView (TilePanel panel, Level level, Nurse nurse) {
        this.panel = panel;
        this.level = level;
    }

    public void updatePosition (Location prevLocation) {
        final LevelElement element = level.getElementAt(prevLocation);
        final Tile tile = panel.getTile(prevLocation.x, prevLocation.y);
        panel.setTile(prevLocation.x, prevLocation.y, null);
        panel.setTile(element.getPosition().x, element.getPosition().y, tile);
    }

    public void init() {
        for (int y = 0; y < level.height; ++y)
            for (int x = 0; x < level.width; ++x) {
                panel.setTile(x, y, level.getElementAt(x, y).getTileClass(panel.getContext()));
            }
    }

    //TODO: check if correct context
    public void printText (int text) {
        Toast.makeText(panel.getContext(), text, Toast.LENGTH_LONG).show();
    }
}
