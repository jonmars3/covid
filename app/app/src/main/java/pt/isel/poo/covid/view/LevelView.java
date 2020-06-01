package pt.isel.poo.covid.view;
import pt.isel.poo.covid.model.Hero;
import pt.isel.poo.covid.model.Level;
import pt.isel.poo.covid.model.LevelElement;
import pt.isel.poo.covid.model.Location;
import pt.isel.poo.covid.model.TrashCan;
import pt.isel.poo.covid.model.Virus;
import pt.isel.poo.covid.model.Wall;
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

    private void initialize() {
        for(int x = 0; x < level.arenaWidth; ++x) {
            for(int y = 0; y < level.arenaHeight; ++y) {
                // TODO: Prevent the instantiation of all these objects
                updatePosition(new Location(x, y));

            }
        }
    }

    private void updatePosition(Location position) {
        final LevelElement element = level.getElementAt(position.x, position.y);
        Tile tile = null;

        //System.out.println("X:" + position.x + " Y:" + position.y + " " + " Element:" + element);

        if (element instanceof Hero)
            tile = new HeroTile();

        if (element instanceof TrashCan)
            tile = new TrashCanTile();

        if  (element instanceof Wall)
            tile = new WallTile();

        if  (element instanceof Virus)
            tile = new VirusTile();

        panel.setTile(position.x, position.y, tile);
    }
}
