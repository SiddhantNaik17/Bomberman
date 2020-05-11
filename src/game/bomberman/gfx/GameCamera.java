package game.bomberman.gfx;

import game.bomberman.Handler;
import game.bomberman.entities.Entity;
import game.bomberman.tiles.Tile;

public class GameCamera {

    private Handler handler;
    private float xOffset, yOffset;

    public GameCamera(Handler handler, float xOffset, float yOffset) {
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public  void checkBlankSpace() {
        if (xOffset < 0)
            xOffset = 0;
        else if (xOffset > handler.getWorld().getWidth() * Tile.TILE_WIDTH - handler.getWidth())
            xOffset = handler.getWorld().getWidth() * Tile.TILE_WIDTH - handler.getWidth();

        /*
        if (yOffset < 0)
            yOffset = 0;
        else if (yOffset > handler.getWorld().getHeight() * Tile.TILE_HEIGHT - handler.getHeight())
            yOffset = handler.getWorld().getHeight() * Tile.TILE_HEIGHT - handler.getHeight();
         */
    }

    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - (handler.getWidth() >> 1) + (e.getWidth() >> 1);
        //yOffset = e.getY() - (handler.getHeight() >> 1) + (e.getHeight() >> 1);
        checkBlankSpace();
    }

    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }
}
