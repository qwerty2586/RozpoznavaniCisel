package cz.zcu.qwerty;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Cli {

    public static void  run (String training_directory,String testing_directory,int vectoring,int classifier,String model_filename) {
        Model model;
        try {
            model = Model.makeModel(training_directory);
        } catch (IOException e) {
            System.out.println("Nepodařilo se načíst trénovací data");
            return;
        }

        System.out.println("Úspěšně načteno "+model.resultMap.length+" etalonů z adresáře "+training_directory);

        try {
            Model.save(model,model_filename);
        } catch (IOException e) {
            System.out.println("Nepodařilo se uložit model");
            return;
        }

        System.out.println("Úspěšné uložení modelu do souboru " +model_filename);

        int[][] etalons = new int[0][0];
        switch (vectoring) {
            case Vectoring.HISTOGRAM: etalons = model.histogramEtalons; break;
            case Vectoring.PROPORTIONS: etalons = model.proportionsEtalons;  break;
            case Vectoring.KEXIK: etalons = model.kexikEtalons; break;
        }


        try {
            if (testing_directory.charAt(testing_directory.length() - 1) != '/')
                testing_directory += '/'; //pridame lomitko na konec

            for (int i = 0; i < 10; i++) {
                File dir = new File(testing_directory + i);
                if (!dir.isDirectory()) throw new IOException();
                File[] files = dir.listFiles();
                for (int j = 0; j < files.length; j++) {
                    FileInputStream is = new FileInputStream(files[j]);
                    int [][] bm = Preprocessing.loadPPM(is);
                    double center[] = Preprocessing.centerOfGravity(bm);
                    bm = Preprocessing.shift(bm,(DrawingPanel.WIDTH/2)-(int)center[0],(DrawingPanel.HEIGHT/2)-(int)center[1]);

                    int[] sample =new int[0];

                    switch (vectoring) {
                        case Vectoring.HISTOGRAM: sample = Vectoring.histogram(bm); break;
                        case Vectoring.PROPORTIONS: sample = Vectoring.proportions(bm);  break;
                        case Vectoring.KEXIK: sample = Vectoring.kexik(bm); break;
                    }
                    int result = -1;
                    if (classifier==Classification.LOWEST_DISTANCE) {
                        result = model.resultMap[Classification.lowestDistance(Classification.MANHATTAN, sample, etalons)];
                    } else {
                        if (vectoring==Vectoring.KEXIK) result = Classification.naive_bayes(sample,etalons,model.resultMap);
                        else result = Classification.naive_bayes(Classification.divide(sample),Classification.divide(etalons),model.resultMap);
                    }

                    System.out.println("Vzorek: "+files[j].getPath()+" klasifikován jako: "+result);


                }
            }
        } catch (IOException e) {
            System.out.println("Chyba při čtení souboru");
        }



    }
}
