package handlers;

import events.Wave;
import scenes.Playing;

import java.util.ArrayList;
import java.util.Arrays;

import static helperMethods.Constants.Enemies.*;

public class WaveHandler {
    private Playing playing;
    private ArrayList<Wave> waves = new ArrayList<>();
    private int enemySpawnTickLimit = 60 * 1;
    private int enemySpawnTick = enemySpawnTickLimit;
    int enemyIndex, waveIndex;
    private int waveTickLimit = 60 * 5;
    private int waveTick = 0;
    private boolean waveStartTimer;
    private boolean waveStartTimerOver;

    public WaveHandler(Playing playing) {
        this.playing = playing;
        createWaves();
    }

    public void update() {
        if(enemySpawnTick < enemySpawnTickLimit) {
            enemySpawnTick++;
        }

        if(waveStartTimer) {
            waveTick++;

            if(waveTick >= waveTickLimit) {
                waveStartTimerOver = true;
            }
        }
    }

    public void increaseWaveIndex() {
        waveIndex++;
        waveStartTimerOver = false;
        waveStartTimer = false;
    }

    public int getNextEnemy() {
        enemySpawnTick = 0;
        return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
    }

    private void createWaves() {
        waves.add(new Wave(new ArrayList<>(Arrays.asList(ORC, ORC))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(ORC,ORC,BAT,BAT))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(ORC,ORC,ORC,ORC,BAT,BAT,BAT))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(ORC,ORC,BAT,BAT,WOLF,WOLF))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(ORC,ORC,ORC,ORC,WOLF,WOLF,WOLF,WOLF))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(WOLF,WOLF,WOLF,WOLF,WOLF,WOLF,WOLF,WOLF,WOLF,WOLF,WOLF,WOLF))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(ORC,ORC,ORC,ORC,KNIGHT,BAT,BAT,WOLF,WOLF))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(KNIGHT,BAT,BAT,BAT,BAT,WOLF, WOLF, WOLF, WOLF))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(ORC,ORC,KNIGHT,KNIGHT,KNIGHT,KNIGHT,WOLF,WOLF,WOLF,WOLF,WOLF,WOLF))));
    }

    public ArrayList<Wave> getWaves() {
        return waves;
    }

    public boolean isTimeForNewEnemy() {
        return enemySpawnTick >= enemySpawnTickLimit;
    }

    public boolean isThereMoreEnemiesInWave() {
        return enemyIndex < waves.get(waveIndex).getEnemyList().size();
    }

    public boolean isThereMoreWaves() {
        return waveIndex + 1 < waves.size();
    }

    public void startWaveTimer() {
        waveStartTimer = true;
    }

    public boolean isWaveTimerOver() {
        return waveStartTimerOver;
    }

    public void resetEnemyIndex() {
        enemyIndex = 0;
    }

    public int getWaveIndex() {
        return waveIndex;
    }

    public float getTimeLeft() {
        float ticksLeft = waveTickLimit - waveTick;

        return ticksLeft / 60.0f;
    }

    public boolean isWaveTimerStarted() {
        return waveStartTimer;
    }

    public void reset() {
        waves.clear();
        createWaves();
        enemyIndex = 0;
        waveIndex = 0;
        waveStartTimer = false;
        waveStartTimerOver = false;
        waveTick = 0;
        enemySpawnTick = enemySpawnTickLimit;
    }
}
