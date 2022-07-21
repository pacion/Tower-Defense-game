package enemies;

import handlers.EnemyHandler;

import static helperMethods.Constants.Enemies.WOLF;

public class Wolf extends Enemy {
    public Wolf(float x, float y, int id, EnemyHandler eh) {
        super(x, y, id, WOLF, eh);
    }
}
