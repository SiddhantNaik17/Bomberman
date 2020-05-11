package game.bomberman.entities.creature;

import game.bomberman.Handler;
import game.bomberman.entities.Entity;
import game.bomberman.gfx.Animation;
import game.bomberman.gfx.Asset;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends Creature {

    private Animation animLeft, animRight, animDeath;

    private enum Direction { Right, Left, Up, Down }
    private Direction currDirection;

    public Enemy(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
            handler.getWorld().getEntityManager().getPlayer().kill();

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
