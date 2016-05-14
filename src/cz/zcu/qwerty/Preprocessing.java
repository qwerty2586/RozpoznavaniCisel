package cz.zcu.qwerty;

public class Preprocessing {
    public static double[] centerOfGravity(int[][]bitmap) {
        double[] r = new double[2];
        r[0] = 0.0; //x
        r[1] = 0.0; //y
        double sum = 0.0;

        int cols = bitmap[0].length;
        int rows = bitmap.length;
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                r[0] += (255-bitmap[x][y]) * x;
                r[1] += (255-bitmap[x][y]) * y;
                sum += (255-bitmap[x][y]);
            }
        }
        r[0] /= sum;
        r[1] /= sum;
        return r;
    }
    public static int[][] shift(int[][] bitmap,int shiftx,int shifty) {
        int cols = bitmap[0].length;
        int rows = bitmap.length;

        int[][] r = new int[cols][rows];
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                r[x][y] = bitmap[(x-shiftx+cols)%cols][(y-shifty+rows)%rows];
            }
        }

        return r;

    }
}
