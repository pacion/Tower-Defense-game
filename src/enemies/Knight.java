package enemies;

import handlers.EnemyHandler;

import static helperMethods.Constants.Enemies.KNIGHT;

public class Knight extends Enemy {
    public Knight(float x, float y, int id, EnemyHandler eh) {
        super(x, y, id, KNIGHT, eh);
    }
}
