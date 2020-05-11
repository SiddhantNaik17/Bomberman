package game.bomberman.entities.creature;

import game.bomberman.Handler;
import game.bomberman.entities.Entity;
import game.bomberman.entities.statics.Bomb;
import game.bomberman.gfx.Animation;
import game.bomberman.gfx.Asset;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    private Animation animRight, animLeft, animUp, animDown, animDeath;

    private BufferedImage lastFrame;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);

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
