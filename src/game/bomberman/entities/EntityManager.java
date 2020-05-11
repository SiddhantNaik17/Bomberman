package game.bomberman.entities;

import game.bomberman.Handler;
import game.bomberman.entities.creature.Enemy;
import game.bomberman.entities.creature.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class EntityManager {

    private Handler handler;
    private Player player;
    private int enemyCount;
    private ArrayList<Entity> entities;

    public EntityManager(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        enemyCount = 0;

        entities = new ArrayList<>();
        addEntity(player);
    }

    public void tick() {
        try {
            for (Entity e : entities) {
                e.tick();
            }
        } catch (ConcurrentModificationException e) {
            e.getStackTrace();
        }
    }

    public void render(Graphics g) {
        for (Entity e : entities) {
            e.render(g);
        }
    }

    public void addEntity(Entity e) {
        if (e instanceof Enemy)
            enemyCount++;

        entities.add(e);
    }

    public void removeEntity(Entity e) {
        entities.remove(e);
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public int getEnemyCount() {
        return enemyCount;
    }
}
