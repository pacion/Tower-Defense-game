package helperMethods;

import objects.PathPoint;

import java.util.ArrayList;

import static helperMethods.Constants.Direction.*;
import static helperMethods.Constants.Direction.RIGHT;
import static helperMethods.Constants.Tiles.ROAD_TILE;

public class Utils {
    public static int[][] GetRoadDirectionArray(int[][] lvlTypeArray, PathPoint start, PathPoint end) {
        int[][] roadDirectionArray = new int[lvlTypeArray.length][lvlTypeArray[0].length];

        PathPoint currentTile = start;
        int lastDirection = -1;

        while(!IsCurrentSameAsEnd(currentTile, end)) {
            PathPoint previousTile = currentTile;
            currentTile = GetNextRoadTile(previousTile, lastDirection, lvlTypeArray);

            lastDirection = GetDirectionFromPreviousTileToCurrentTile(previousTile, currentTile);
            roadDirectionArray[previousTile.getY()][previousTile.getX()] = lastDirection;
        }

        System.out.println("ku");


        roadDirectionArray[end.getY()][end.getX()] = lastDirection;

        return roadDirectionArray;
    }

    private static int GetDirectionFromPreviousTileToCurrentTile(PathPoint previousTile, PathPoint currentTile) {
        if(previousTile.getX() == currentTile.getX()) {
            if(previousTile.getY() > currentTile.getY()) {
                return UP;
            } else {
                return DOWN;
            }
        } else {
            if(previousTile.getX() > currentTile.getX()) {
                return LEFT;
            } else {
                return RIGHT;
            }
        }
    }

    private static PathPoint GetNextRoadTile(PathPoint previousTile, int lastDirection, int[][] lvlTypeArray) {
        int testDirection = lastDirection;

        PathPoint testTile = GetTileInDirection(previousTile, testDirection, lastDirection);

        while(!IsTileRoad(testTile, lvlTypeArray)) {
            testDirection++;
            testDirection %= 4;

            testTile = GetTileInDirection(previousTile, testDirection, lastDirection);
        }

        return testTile;
    }

    private static boolean IsTileRoad(PathPoint testTile, int[][] lvlTypeArray) {
        if(testTile != null)
            if(testTile.getY() >= 0 && testTile.getY() < lvlTypeArray.length)
                if(testTile.getX() >= 0 && testTile.getX() < lvlTypeArray[0].length)
                    if(lvlTypeArray[testTile.getY()][testTile.getX()] == ROAD_TILE)
                        return true;

        return false;
    }

    private static PathPoint GetTileInDirection(PathPoint previousTile, int testDirection, int lastDirection) {
        switch(testDirection) {
            case LEFT:
                if(lastDirection != RIGHT)
                    return new PathPoint(previousTile.getX() - 1, previousTile.getY());
            case UP:
                if(lastDirection != DOWN)
                    return new PathPoint(previousTile.getX(), previousTile.getY() - 1);
            case RIGHT:
                if(lastDirection != LEFT)
                    return new PathPoint(previousTile.getX() + 1, previousTile.getY());
            case DOWN:
                if(lastDirection != UP)
                    return new PathPoint(previousTile.getX(), previousTile.getY() + 1);
        }

        return null;
    }

    private static boolean IsCurrentSameAsEnd(PathPoint currentTile, PathPoint end) {
        if (currentTile.getX() == end.getX())
            if (currentTile.getY() == end.getY())
                return true;

        return false;
    }

    public static int[][] ArrayListTo2DInt(ArrayList<Integer> list, int x, int y) {
        int [][] newArr = new int[y][x];

        for(int i = 0; i < newArr.length; ++i) {
            for(int j = 0; j < newArr[i].length; ++j) {
                int index = i * y + j;
                newArr[i][j] = list.get(index);
            }
        }

        return newArr;
    }

    public static int[] Flat2DArray (int[][] twoArr){
        int[] oneArr = new int[twoArr.length * twoArr[0].length];

        for(int i = 0; i < twoArr.length; ++i) {
            for(int j = 0; j < twoArr[i].length; ++j) {
                int index = i * twoArr.length + j;
                oneArr[index] = twoArr[i][j];
            }
        }

        return oneArr;
    }

    public static int GetHypotDistance(float x1, float y1, float x2, float y2) {
        float xDiff = Math.abs(x1 - x2);
        float yDiff = Math.abs(y1 - y2);

        return (int)Math.hypot(xDiff, yDiff);

    }
}
