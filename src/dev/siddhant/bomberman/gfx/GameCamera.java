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

package dev.siddhant.bomberman.gfx;

import dev.siddhant.bomberman.Handler;
import dev.siddhant.bomberman.entities.Entity;
import dev.siddhant.bomberman.tiles.Tile;

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
