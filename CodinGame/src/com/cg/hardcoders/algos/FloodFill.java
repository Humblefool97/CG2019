package com.cg.hardcoders.algos;

public class FloodFill {
    private static final int row = 8;
    private static final int column = 8;


    private static void floodFillUtil(int[][] arr, int x, int y, int prevValue, int newValue) {
        //boundry condition
        if (x < 0 || x >= row || y < 0 || y >= column) return;
        //if it's not the same value you visited previously,FO!!
        if (arr[x][y] != prevValue) return;

        arr[x][y] = newValue;
        //Check for all four directions
        floodFillUtil(arr, x + 1, y, prevValue, newValue);
        floodFillUtil(arr, x - 1, y, prevValue, newValue);
        floodFillUtil(arr, x, y + 1, prevValue, newValue);
        floodFillUtil(arr, x, y - 1, prevValue, newValue);
    }

    private static void floodFill(int[][] arr, int x, int y, int newValue) {
        floodFillUtil(arr, x, y, arr[x][y], newValue);
    }

    public static void main(String[] args) {
        int screen[][] = {{1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 0, 0},
                {1, 0, 0, 1, 1, 0, 1, 1},
                {1, 2, 2, 2, 2, 0, 1, 0},
                {1, 1, 1, 2, 2, 0, 1, 0},
                {1, 1, 1, 2, 2, 2, 2, 0},
                {1, 1, 1, 1, 1, 2, 1, 1},
                {1, 1, 1, 1, 1, 2, 2, 1},
        };
        int x = 4, y = 4, newC = 3;
        floodFill(screen, x, y, newC);

        System.out.println("Updated screen after call to floodFill: ");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++)
                System.out.print(screen[i][j] + " ");
            System.out.println();
        }
    }
}
