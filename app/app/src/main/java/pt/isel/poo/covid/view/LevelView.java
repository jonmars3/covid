package pt.isel.poo.covid.view;

import pt.isel.poo.covid.model.Level;
import pt.isel.poo.covid.model.LevelElement;
import pt.isel.poo.covid.model.Location;

import pt.isel.poo.covid.tile.Tile;
import pt.isel.poo.covid.tile.TilePanel;

public class LevelView {

    private final TilePanel panel;
    private final Level level;



    public LevelView(TilePanel panel, Level level) {
        this.panel = panel;
        this.level = level;
        initialize();
        
    }

    
    public void onChange(int x1,int y1,int x2, int y2){
        updatePosition(new Location(x1,y1));
        updatePosition(new Location(x2, y2));

    }

    private void initialize() {
        for(int x = 0; x < level.arenaWidth; ++x) {
            for(int y = 0; y < level.arenaHeight; ++y) {
                updatePosition(new Location(x, y));

            }
        }
    }

    private void updatePosition(Location position) {
        final LevelElement element = level.getElementAt(position.x, position.y);
        Tile tile = null;
        if(element!=null) tile = element.tileType(panel.getContext());
        panel.setTile(position.x, position.y,tile);
    }
}
