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
import dev.siddhant.bomberman.entities.statics.Bomb;
import dev.siddhant.bomberman.gfx.Animation;
import dev.siddhant.bomberman.gfx.Asset;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    private Animation animRight, animLeft, animUp, animDown, animDeath;

    private BufferedImage lastFrame;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT);

        animRight = new Animation(100, Asset.playerRight);
        animLeft = new Animation(100, Asset.playerLeft);
        animUp = new Animation(100, Asset.playerUp);
        animDown = new Animation(100, Asset.playerDown);
        animDeath = new Animation(100, Asset.playerDeath);

        lastFrame = animDown.getCurrentFrame();
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
        animUp.tick();
        animDown.tick();

        getInput();
        move();

        handler.getGameCamera().centerOnEntity(this);
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        if (handler.getKeyManager().right)
            xMove = speed;

        if (handler.getKeyManager().left)
            xMove = -speed;

        if (handler.getKeyManager().up)
            yMove = -speed;

        if (handler.getKeyManager().down)
            yMove = speed;

        if (handler.getKeyManager().bomb)
            handler.getWorld().getEntityManager().addEntity(new Bomb(handler, getX(), getY()));

        if (checkEnemyCollisions(xMove, yMove))
            kill();
    }

    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (xMove > 0)
            lastFrame = animRight.getCurrentFrame();

        if (xMove < 0)
            lastFrame = animLeft.getCurrentFrame();

        if (yMove > 0)
            lastFrame = animDown.getCurrentFrame();

        if (yMove < 0)
            lastFrame = animUp.getCurrentFrame();

        if (isKilled())
            lastFrame = animDeath.getCurrentFrame();

        return lastFrame;
    }

    public boolean checkEnemyCollisions(float xOffset, float yOffset) {
        for (Entity e: handler.getWorld().getEntityManager().getEntities()) {
            if (e.equals(this))
                continue;
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)) && e instanceof Enemy)
                return true;
        }

        return false;
    }
}
