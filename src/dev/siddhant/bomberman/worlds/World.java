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

package dev.siddhant.bomberman.worlds;

import dev.siddhant.bomberman.Handler;
import dev.siddhant.bomberman.entities.EntityManager;
import dev.siddhant.bomberman.entities.creature.Enemy;
import dev.siddhant.bomberman.entities.creature.Player;
import dev.siddhant.bomberman.entities.statics.Brick;
import dev.siddhant.bomberman.tiles.Tile;
import dev.siddhant.bomberman.utils.Utils;

import java.awt.*;
import java.util.Random;

public class World {

    private Handler handler;
    private int width, height;
    private int spawnX, spawnY;

    private int[][] tiles;

    private EntityManager entityManager;


    public World(Handler handler, String path) {
        this.handler = handler;

        entityManager = new EntityManager(handler, new Player(handler, Tile.TILE_WIDTH, Tile.TILE_HEIGHT));

        Random ran = new Random();

        for (int i=1; i<12; i++) {
            for (int j=2; j<30; j++) {
                if (j%2 == 0)
                    continue;
                if (ran.nextInt() % 2 == 0)
                    continue;
                entityManager.addEntity(new Brick(handler, Tile.TILE_WIDTH * j, Tile.TILE_HEIGHT * i));
            }
        }

        while(entityManager.getEnemyCount() < 6) {
            entityManager.addEntity(new Enemy(handler, Tile.TILE_WIDTH * ran.nextInt(30),
                    Tile.TILE_HEIGHT * ran.nextInt(12)));
        }

        loadWorld(path);

        //entityManager.getPlayer().setX(spawnX);
        //entityManager.getPlayer().setY(spawnY);
    }

    public void tick() {
        entityManager.tick();
    }

    public void render(Graphics g) {
        int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILE_HEIGHT + 1);

        for (int y=yStart; y<yEnd; y++) {
            for (int x=xStart; x<xEnd; x++) {
                getTile(x, y).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getxOffset()),
                        (int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getyOffset()));
            }
        }

        entityManager.render(g);
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return Tile.rockTile;

        Tile t = Tile.tiles[tiles[x][y]];

        if (t == null)
            return Tile.backgroundTile;

        return t;
    }

    private void loadWorld(String path) {
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");

        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);

        tiles = new int[width][height];
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
