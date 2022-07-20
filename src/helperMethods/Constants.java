package helperMethods;

public class Constants {
    public static class Projectiles {
        public static final int ARROW = 0;
        public static final int BOMB = 2;
        public static final int ICE = 1;

        public static float GetSpeed(float type) {
            if(type == ARROW) {
                return 3.2f;
            } else if(type == BOMB) {
                return 2.2f;
            } else if(type == ICE) {
                return 2.1f;
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

        public static int GetReward(int enemyType) {
            if(enemyType == ORC) {
                return 10;
            } else if(enemyType == BAT) {
                return 14;
            } else if(enemyType == KNIGHT) {
                return 27;
            } else if(enemyType == WOLF) {
                return 16;
            }

            return 0;
        }

        public static float GetSpeed(int enemyType) {
            if(enemyType == ORC) {
                return 0.45f;
            } else if(enemyType == BAT) {
                return 0.60f;
            } else if(enemyType == KNIGHT) {
                return 0.2f;
            } else if(enemyType == WOLF) {
                return 1.1f;
            }

            return 0;
        }

        public static int GetStartHealth(int enemyType) {
            if(enemyType == ORC) {
                return 1500;
            } else if(enemyType == BAT) {
                return 1200;
            } else if(enemyType == KNIGHT) {
                return 100000;
            } else if(enemyType == WOLF) {
                return 2900;
            }

            return 0;
        }
    }

    public static class Towers {
        public static final int CANNON = 0;
        public static final int ARCHER = 1;
        public static final int WIZARD = 2;

        public static int GetTowerCost(int towerType) {
            if(towerType == CANNON) {
                return 65;
            } else if(towerType == ARCHER) {
                return 40;
            } else if(towerType == WIZARD) {
                return 50;
            }

            return 0;
        }

        public static int GetStartDamage(int towerType) {
            if(towerType == CANNON) {
                return 95;
            } else if(towerType == ARCHER) {
                return 43;
            } else if(towerType == WIZARD) {
                return 15;
            }

            return 0;
        }

        public static float GetDefaultRange(int towerType) {
            if(towerType == CANNON) {
                return 80;
            } else if(towerType == ARCHER) {
                return 90;
            } else if(towerType == WIZARD) {
                return 110;
            }

            return 0;
        }

        public static float GetDefaultCooldown(int towerType) {
            if(towerType == CANNON) {
                return 84f;
            } else if(towerType == ARCHER) {
                return 32f;
            } else if(towerType == WIZARD) {
                return 44f;
            }

            return 0;
        }

        public static String GetName(int towerType) {
            if(towerType == CANNON) {
                return "Cannon";
            } else if(towerType == ARCHER) {
                return "Archer";
            } else if(towerType == WIZARD) {
                return "Wizard";
            }

            return "";
        }
    }
}
