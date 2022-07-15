package helperMethods;

public class Constants {
    public static class Projectiles {
        public static final int ARROW = 0;
        public static final int BOMB = 1;
        public static final int ICE = 2;

        public static float GetSpeed(float type) {
            if(type == ARROW) {
                return 3f;
            } else if(type == BOMB) {
                return 1f;
            } else if(type == ICE) {
                return 2f;
            }
            return 0;
        }
    }

    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 4;
    }

    public static class Tiles {
        public static final int WATER_TILE = 0;
        public static final int GRASS_TILE = 1;
        public static final int ROAD_TILE = 2;
    }

    public static class Enemies {
        public static final int ORC = 0;
        public static final int BAT = 1;
        public static final int KNIGHT = 2;
        public static final int WOLF = 3;

        public static float GetSpeed(int enemyType) {
            if(enemyType == ORC) {
                return 0.5f;
            } else if(enemyType == BAT) {
                return 0.75f;
            } else if(enemyType == KNIGHT) {
                return 0.2f;
            } else if(enemyType == WOLF) {
                return 1.2f;
            }

            return 0;
        }

        public static int GetStartHealth(int enemyType) {
            if(enemyType == ORC) {
                return 100;
            } else if(enemyType == BAT) {
                return 75;
            } else if(enemyType == KNIGHT) {
                return 300;
            } else if(enemyType == WOLF) {
                return 20;
            }

            return 0;
        }
    }

    public static class Towers {
        public static final int CANNON = 2;
        public static final int ARCHER = 1;
        public static final int WIZARD = 0;

        public static String getName(int towerType) {
            if(towerType == CANNON) {
                return "Cannon";
            } else if(towerType == ARCHER) {
                return "Archer";
            } else if(towerType == WIZARD) {
                return "Wizard";
            }

            return "";
        }

        public static int GetStartDamage(int towerType) {
            if(towerType == CANNON) {
                return 10;
            } else if(towerType == ARCHER) {
                return 7;
            } else if(towerType == WIZARD) {
                return 12;
            }

            return 0;
        }

        public static float GetDefaultRange(int towerType) {
            if(towerType == CANNON) {
                return 60;
            } else if(towerType == ARCHER) {
                return 180;
            } else if(towerType == WIZARD) {
                return 100;
            }

            return 0;
        }

        public static float GetDefaultCooldown(int towerType) {
            if(towerType == CANNON) {
                return 10f;
            } else if(towerType == ARCHER) {
                return 6f;
            } else if(towerType == WIZARD) {
                return 15f;
            }

            return 0;
        }
    }
}
