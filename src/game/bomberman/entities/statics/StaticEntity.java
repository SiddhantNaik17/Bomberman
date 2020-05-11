package game.bomberman.entities.statics;

import game.bomberman.Handler;
import game.bomberman.entities.Entity;

public abstract class StaticEntity extends Entity {

    public StaticEntity(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
    }
}
