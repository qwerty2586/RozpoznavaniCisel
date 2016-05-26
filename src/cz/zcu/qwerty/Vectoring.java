package cz.zcu.qwerty;


public class Vectoring {

    static int DEFAULT_HISTOGRAM_TRESHOLD =128;
    static int PROPORTIONS_TRESHOLD =200;
    public static final int COUNT = 3;

    public static int[] histogram(int[][] bitmap) {
        return histogram(bitmap, DEFAULT_HISTOGRAM_TRESHOLD);
    }

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

    static int RESOLUTION =30;

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
                if (v<PROPORTIONS_TRESHOLD) {
                    if (minx>x) minx = x;
                    if (miny>y) miny = y;
                    if (maxx<x) maxx = x;
                    if (maxy<y) maxy = y;
                }
            }
        }
        // mame minima maxima
        for (int i = 0; i < RESOLUTION; i++) {
            int row = miny + (((maxy - miny)/(RESOLUTION+1)) * i+1); //urcime radek
            int row_min = cols;
            int row_max = 0;
            for (int j = 0; j < cols; j++) {
                if (bitmap[j][row]<PROPORTIONS_TRESHOLD) {
                    if (j<row_min) row_min = j;
                    if (j>row_max) row_max = j;
                }
            }
            r[i] = ((row_max-row_min)*255)/(maxx-minx);
        }

        for (int i = 0; i < RESOLUTION; i++) {
            int col = minx + (((maxx - minx)/(RESOLUTION+1)) * i+1); //urcime radek
            int col_min = rows;
            int col_max = 0;
            for (int j = 0; j < rows; j++) {
                if (bitmap[col][j]<PROPORTIONS_TRESHOLD) {
                    if (j<col_min) col_min = j;
                    if (j>col_max) col_max = j;
                }
            }
            r[i+RESOLUTION] = ((col_max-col_min)*255)/(maxy-miny);
        }

        return r;

    }
    private static boolean isBlack(int i){
        return i<PROPORTIONS_TRESHOLD;
    }

    private static boolean isWhite(int i){
        return !isBlack(i);
    }


    public static int[] kexik(int[][]bitmap) {
        int cols = bitmap[0].length;
        int rows = bitmap.length;
        int[] r = new int[RESOLUTION*4];
        int minx = cols;
        int maxx = 0;
        int miny = rows;
        int maxy = 0;
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                int v = bitmap[x][y];
                if (isBlack(v)) {
                    if (minx>x) minx = x;
                    if (miny>y) miny = y;
                    if (maxx<x) maxx = x;
                    if (maxy<y) maxy = y;
                }
            }
        }
        // mame minima maxima
        for (int i = 0; i < RESOLUTION; i++) {
            int row = miny + (((maxy - miny)/(RESOLUTION+1)) * i+1); //urcime radek
            int start = -1;
            int end = -1;
            int shortBlack = 0;
            int longBlack = 0;

            boolean lastBlack = false; //jakoze prvni je bila

            for (int j = minx; j <= maxx+1; j++) {
                boolean actualBlack = false;
                if (j<128) actualBlack = isBlack(bitmap[j][row]);
                if (!lastBlack&&actualBlack) {start = j;}
                if (lastBlack&&!actualBlack) {
                    end = j;
                    int length = end-start;
                    if (length<((maxx-minx)/2)) {
                        shortBlack++;
                    } else shortBlack++;


                }
                lastBlack = actualBlack;
            }
            r[i*2] = shortBlack;
            r[(i*2)+1] = longBlack*10;

        }

        for (int i = 0; i < RESOLUTION; i++) {
            int col = minx + (((maxx - minx)/(RESOLUTION+1)) * i+1); //urcime radek
            int start = -1;
            int end = -1;
            int shortBlack = 0;
            int longBlack = 0;

            boolean lastBlack = false; //jakoze prvni je bila

            for (int j = miny; j <= maxy+1; j++) {
                boolean actualBlack = false;
                if (j<128) actualBlack = isBlack(bitmap[col][j]);
                if (!lastBlack&&actualBlack) {start = j;}
                if (lastBlack&&!actualBlack) {
                    end = j;
                    int length = end-start;
                    if (length<((maxy-miny)/2)) {
                        shortBlack++;
                    } else shortBlack++;


                }
                lastBlack = actualBlack;
            }
            r[RESOLUTION+RESOLUTION+i*2] = shortBlack;
            r[RESOLUTION+RESOLUTION+(i*2)+1] = longBlack*10;

        }


        return r;

    }


}
