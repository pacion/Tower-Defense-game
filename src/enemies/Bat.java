package enemies;

import handlers.EnemyHandler;

import static helperMethods.Constants.Enemies.BAT;

public class Bat extends Enemy{
    public Bat(float x, float y, int id, EnemyHandler eh) {
        super(x, y, id, BAT, eh);
    }
}
