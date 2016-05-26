package cz.zcu.qwerty;


import java.io.*;
import java.util.Scanner;

public class Model
{
    public int[][] proportionsEtalons;
    public int[][] histogramEtalons;
    public int[][] kexikEtalons;
    public int[] resultMap;

    public Model(int etalons_count) {
        resultMap = new int[etalons_count];
        histogramEtalons = new int[etalons_count][];
        proportionsEtalons = new int[etalons_count][];
        kexikEtalons = new int[etalons_count][];
    }

    public static Model makeModel(String directory) throws IOException {
        int total_length = 0;
        if (directory.charAt(directory.length()-1)!='/') directory+='/';
        File f;
        for (int i = 0; i < 10; i++) {
            f = new File(directory+i);
            if (f.isDirectory()) {
                total_length += f.listFiles().length;
            } else {
                throw new IOException();
            }
        }

        Model model = new Model(total_length);

        int it = 0;

        for (int i = 0; i < 10; i++) {
            f = new File(directory+i);
            File[] files = f.listFiles();
            for (int j = 0; j < files.length; j++) { // tady uz prochazime soubory
                FileInputStream is = new FileInputStream(files[j]);
                int [][] pom = Preprocessing.loadPPM(is);
                double center[] = Preprocessing.centerOfGravity(pom);
                pom = Preprocessing.shift(pom,(DrawingPanel.WIDTH/2)-(int)center[0],(DrawingPanel.HEIGHT/2)-(int)center[1]);

                model.proportionsEtalons[it] = Vectoring.proportions(pom);
                model.histogramEtalons[it] = Vectoring.histogram(pom);
                model.kexikEtalons[it] = Vectoring.kexik(pom);
                model.resultMap[it] = i;
                it++;

                is.close();
            }

        }

        return model;
    }

    public static void save(Model model, String filename) throws IOException {
        FileWriter fw = new FileWriter(filename);
        int etalons_count = model.resultMap.length;
        fw.write("" + etalons_count + "\r\n");
        for (int i = 0; i < etalons_count; i++) {
            fw.write(""+model.resultMap[i]+ " ");
        }
        fw.write("\r\n");

        int histogram_length = model.histogramEtalons[0].length;
        fw.write("" + histogram_length + "\r\n");
        for (int i = 0; i < etalons_count; i++) {
            for (int j = 0; j < histogram_length; j++) {
                fw.write(""+model.histogramEtalons[i][j]+ " ");
            }
            fw.write("\r\n");
        }

        int proportions_length = model.proportionsEtalons[0].length;
        fw.write("" + proportions_length + "\r\n");
        for (int i = 0; i < etalons_count; i++) {
            for (int j = 0; j < proportions_length; j++) {
                fw.write(""+model.proportionsEtalons[i][j]+ " ");
            }
            fw.write("\r\n");
        }

        int kexik_length= model.kexikEtalons[0].length;
        fw.write("" + kexik_length + "\r\n");
        for (int i = 0; i < etalons_count; i++) {
            for (int j = 0; j < kexik_length; j++) {
                fw.write(""+model.kexikEtalons[i][j]+ " ");
            }
            fw.write("\r\n");
        }

        fw.write("\r\n");
        fw.flush();
        fw.close();

    }

    public static Model load(String filename) throws IOException {
        Scanner sc =  new Scanner(new FileReader(filename));
        int etalons_count = sc.nextInt();
        Model model = new Model(etalons_count);
        for (int i = 0; i < etalons_count; i++) {
            model.resultMap[i] = sc.nextInt();
        }

        int histogram_length = sc.nextInt();
        for (int i = 0; i < etalons_count; i++) {
            model.histogramEtalons[i] = new int [histogram_length];
            for (int j = 0; j < histogram_length; j++) {
                model.histogramEtalons[i][j] = sc.nextInt();
            }
        }

        int proportions_length = sc.nextInt();
        for (int i = 0; i < etalons_count; i++) {
            model.proportionsEtalons[i] = new int [proportions_length];
            for (int j = 0; j < proportions_length; j++) {
                model.proportionsEtalons[i][j] = sc.nextInt();
            }
        }

        int kexik_length = sc.nextInt();
        for (int i = 0; i < etalons_count; i++) {
            model.kexikEtalons[i] = new int [kexik_length];
            for (int j = 0; j < kexik_length; j++) {
                model.kexikEtalons[i][j] = sc.nextInt();
            }
        }

        return model;
    }
}
