package main;

public enum GameStates {
    PLAYING, MENU, SETTINGS, EDIT, GAME_OVER;

    public static GameStates gameState = GAME_OVER; // MENU

    public static void SetGameState(GameStates state) {
        gameState = state;
    }
}
