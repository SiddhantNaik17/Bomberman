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

package dev.siddhant.bomberman.entities.creature;

import dev.siddhant.bomberman.Handler;
import dev.siddhant.bomberman.entities.Entity;
import dev.siddhant.bomberman.gfx.Animation;
import dev.siddhant.bomberman.gfx.Asset;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends Creature {

    private Animation animLeft, animRight, animDeath;

    private enum Direction { Right, Left, Up, Down; }
    private Direction currDirection;

    public Enemy(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT);
        speed = 1.0f;

        animLeft = new Animation(200, Asset.enemyLeft);
        animRight = new Animation(200, Asset.enemyRight);
        animDeath = new Animation(200, Asset.enemyDeath);

        currDirection = Direction.Right;
    }

    public void tick() {

        if (isKilled()) {
            animDeath.tick();

            if (animDeath.isEndOfAnimation())
                handler.getWorld().getEntityManager().removeEntity(this);

            return;
        }

        animRight.tick();
        animLeft.tick();

        autoMove();
    }

    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (isKilled())
            return animDeath.getCurrentFrame();

        if (xMove > 0)
            return animRight.getCurrentFrame();

        if (xMove < 0)
            return animLeft.getCurrentFrame();

        if (yMove > 0)
            return animRight.getCurrentFrame();

        return animLeft.getCurrentFrame();
    }

    public void autoMove() {
        xMove = 0;
        yMove = 0;

        if (currDirection == Direction.Left)
            xMove = -speed;

        if (currDirection == Direction.Right)
            xMove = speed;

        if (currDirection == Direction.Up)
            yMove = -speed;

        if (currDirection == Direction.Down)
            yMove = speed;

        if (checkEntityCollisions(xMove, yMove) || isCollisionWithTile())
            currDirection = Direction.values()[new Random().nextInt(Direction.values().length)];

        if (checkPlayerCollisions(xMove, yMove))
            handler.getGame().endGame();

        move();
    }

    public boolean checkPlayerCollisions(float xOffset, float yOffset) {
        for (Entity e: handler.getWorld().getEntityManager().getEntities()) {
            if (e.equals(this))
                continue;
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)) && e instanceof Player)
                return true;
        }

        return false;
    }
}
