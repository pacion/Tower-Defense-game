package enemies;

import static helperMethods.Constants.Enemies.ORC;

public class Orc extends Enemy{
    public Orc(float x, float y, int id) {
        super(x, y, id, ORC);
        setStartHealth();
    }
}
