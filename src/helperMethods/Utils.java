package helperMethods;

import java.util.ArrayList;

public class Utils {
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
