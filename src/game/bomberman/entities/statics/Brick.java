package game.bomberman.entities.statics;

import game.bomberman.Handler;
import game.bomberman.gfx.Animation;
import game.bomberman.gfx.Asset;
import game.bomberman.tiles.Tile;

import java.awt.*;

public class Brick extends StaticEntity {

    private Animation animBrickBlast;

    public Brick(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        animBrickBlast = new Animation(100, Asset.brickBlast);
    }

    public void tick() {
        if (isKilled())
            animBrickBlast.tick();

        if (animBrickBlast.isEndOfAnimation())
            handler.getWorld().getEntityManager().removeEntity(this);
    }

    public void render(Graphics g) {
        g.drawImage(animBrickBlast.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }
}
