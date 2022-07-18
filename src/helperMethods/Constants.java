package helperMethods;

public class Constants {
    public static class Projectiles {
        public static final int ARROW = 0;
        public static final int BOMB = 2;
        public static final int ICE = 1;

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

        public static int GetReward(int enemyType) {
            if(enemyType == ORC) {
                return 10;
            } else if(enemyType == BAT) {
                return 10;
            } else if(enemyType == KNIGHT) {
                return 20;
            } else if(enemyType == WOLF) {
                return 15;
            }

            return 0;
        }

        public static float GetSpeed(int enemyType) {
            if(enemyType == ORC) {
                return 0.5f;
            } else if(enemyType == BAT) {
                return 0.6f;
            } else if(enemyType == KNIGHT) {
                return 0.3f;
            } else if(enemyType == WOLF) {
                return 0.75f;
            }

            return 0;
        }

        public static int GetStartHealth(int enemyType) {
            if(enemyType == ORC) {
                return 100;
            } else if(enemyType == BAT) {
                return 75;
            } else if(enemyType == KNIGHT) {
                return 800;
            } else if(enemyType == WOLF) {
                return 50;
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
                return 60;
            } else if(towerType == ARCHER) {
                return 35;
            } else if(towerType == WIZARD) {
                return 45;
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

        public static int GetStartDamage(int towerType) {
            if(towerType == CANNON) {
                return 12;
            } else if(towerType == ARCHER) {
                return 5;
            } else if(towerType == WIZARD) {
                return 1;
            }

            return 0;
        }

        public static float GetDefaultRange(int towerType) {
            if(towerType == CANNON) {
                return 80;
            } else if(towerType == ARCHER) {
                return 120;
            } else if(towerType == WIZARD) {
                return 140;
            }

            return 0;
        }

        public static float GetDefaultCooldown(int towerType) {
            if(towerType == CANNON) {
                return 75f;
            } else if(towerType == ARCHER) {
                return 25f;
            } else if(towerType == WIZARD) {
                return 40f;
            }

            return 0;
        }
    }
}
