package game.bomberman.entities.statics;

import game.bomberman.Handler;
import game.bomberman.entities.Entity;
import game.bomberman.gfx.Animation;
import game.bomberman.gfx.Asset;
import game.bomberman.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bomb extends StaticEntity {

    private Animation animBomb, animBombDetonation;

    private boolean detonated;
    private long lastTime, timer;


    public Bomb(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        animBomb = new Animation(300, Asset.bomb);
        animBombDetonation = new Animation(300, Asset.bombDetonation);

        detonated = false;
        lastTime = System.nanoTime();
        timer = 0;
    }

    public void tick() {
        long now = System.nanoTime();
        timer += now - lastTime;
        lastTime = now;

        if (timer >= 4000000000L) {
            detonated = true;
            lastTime = System.nanoTime();
            timer = 0;
        }


        if (detonated) {
            animBombDetonation.tick();
            if (animBombDetonation.isEndOfAnimation()) {
                detonationKilled();
                handler.getWorld().getEntityManager().removeEntity(this);
            }

        }
        else
            animBomb.tick();
    }

    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (detonated)
            return  animBombDetonation.getCurrentFrame();
        else
            return animBomb.getCurrentFrame();
    }

    private void detonationKilled() {
        for (Entity e: handler.getWorld().getEntityManager().getEntities()) {
            if (e.equals(this))
                continue;
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(-width, 0f, 3, 1)) ||
                    e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(0f, -height, 1, 3)))
                e.kill();
        }
    }
}
