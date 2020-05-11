package game.bomberman.tiles;

import game.bomberman.gfx.Asset;

public class RockTile extends Tile {
    public RockTile(int id) {
        super(Asset.rock, id);
    }

    public boolean isSolid() {
        return true;
    }
}
