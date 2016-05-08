package cz.zcu.qwerty;


public class Vectoring {

    public static int[] histogram(int[][]bitmap,int threshold) {
        int cols = bitmap[0].length;
        int rows = bitmap.length;
        int[] r = new int[cols+rows];
        for (int i = 0; i < rows; i++) {
            int sum = 0;
            for (int j = 0; j < cols; j++) {
                if (bitmap[j][i]<threshold) sum++;
            }
            r[i] = sum;
        }
        for (int i = 0; i < cols; i++) {
            int sum = 0;
            for (int j = 0; j < rows; j++) {
                if (bitmap[i][j]<threshold) sum++;
            }
            r[i+cols] = sum;
        }

        return r;
    }

    static int RESOLUTION =10;

    public static int[] proportions(int[][]bitmap) {
        int cols = bitmap[0].length;
        int rows = bitmap.length;
        int[] r = new int[RESOLUTION+RESOLUTION];
        int minx = cols;
        int maxx = 0;
        int miny = rows;
        int maxy = 0;
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                int v = bitmap[x][y];
                if (v<255) {
                    if (minx>x) minx = x;
                    if (miny>y) miny = y;
                    if (maxx<x) maxx = x;
                    if (maxy<y) maxy = y;
                }
            }
        }
        // mame minima maxima
        for (int i = 0; i < RESOLUTION; i++) {
            int row = miny + (((maxy - miny)/RESOLUTION) * i+1); //urcime radek
            int row_min = cols;
            int row_max = 0;
            for (int j = 0; j < cols; j++) {
                if (bitmap[j][row]<255) {
                    if (j<row_min) row_min = j;
                    if (j>row_max) row_max = j;
                }
            }
            r[i] = ((row_max-row_min)*255)/(maxx-minx);
        }

        for (int i = 0; i < RESOLUTION; i++) {
            int col = minx + (((maxx - minx)/RESOLUTION) * i+1); //urcime radek
            int col_min = rows;
            int col_max = 0;
            for (int j = 0; j < rows; j++) {
                if (bitmap[col][j]<255) {
                    if (j<col_min) col_min = j;
                    if (j>col_max) col_max = j;
                }
            }
            r[i+RESOLUTION] = ((col_max-col_min)*255)/(maxy-miny);
        }

        return r;

    }
}
