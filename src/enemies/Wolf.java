package enemies;

import static helperMethods.Constants.Enemies.WOLF;

public class Wolf extends Enemy {
    public Wolf(float x, float y, int id) {
        super(x, y, id, WOLF);
        setStartHealth();
    }
}
