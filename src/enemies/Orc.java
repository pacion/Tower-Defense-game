package enemies;

import handlers.EnemyHandler;

import static helperMethods.Constants.Enemies.ORC;

public class Orc extends Enemy {
    public Orc(float x, float y, int id, EnemyHandler eh) {
        super(x, y, id, ORC, eh);
    }
}
