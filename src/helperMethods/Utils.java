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
}
