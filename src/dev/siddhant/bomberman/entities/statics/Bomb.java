/*
 * MIT License
 *
 * Copyright (c) 2020 Siddhant Naik
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.siddhant.bomberman.entities.statics;

import dev.siddhant.bomberman.Handler;
import dev.siddhant.bomberman.entities.Entity;
import dev.siddhant.bomberman.gfx.Animation;
import dev.siddhant.bomberman.gfx.Asset;
import dev.siddhant.bomberman.tiles.Tile;

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
