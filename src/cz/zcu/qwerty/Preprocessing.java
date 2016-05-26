package cz.zcu.qwerty;

import java.io.*;
import java.util.Scanner;

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

    public static int[][] loadPPM(File file) {
        try {
            return loadPPM(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return null;
        }
    };
    public static int[][] loadPPM(InputStream stream) {
        Scanner sc = new Scanner(new InputStreamReader(stream));
        String type_str = sc.next();
        int type;
        if (type_str.compareTo("P2")==0) type=2;
        else if (type_str.compareTo("P3")==0) type=3;
        else return null;
        while (!sc.hasNextInt()) sc.nextLine();
        int w = sc.nextInt();
        int h = sc.nextInt();
        int[][] bitmap = new int[w][h];
        int depth = sc.nextInt();
        if (depth!=255) return null;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                bitmap[x][y]= sc.nextInt(); if (type==3) {sc.nextInt();sc.nextInt();}
            }
        }
        return bitmap;
    }
}
