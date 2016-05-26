package cz.zcu.qwerty;

import javax.swing.*;
import java.io.IOException;

public class Main {
    private static JFrame frame;
    private static void showGui(String model_filename) {
        Model model;
        try {
            model = Model.load(model_filename);
        } catch (IOException e) {
            System.out.println("Nepodařilo se načíst model");
            return;
        }

        frame = new JFrame();
        MainPanel mainPanel = new MainPanel(model);
        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Rozpoznavani rucne psanych cisel | Milan Hajzman | qwerty2@students.zcu.cz");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    public static final String PROGRAM_NAME = "A13B0303P.jar";

    public static void showHelp() {
        System.out.println("Špatné argumenty");
        System.out.println("GUI: java -jar "+PROGRAM_NAME+" soubor_modelu");
        System.out.println("CLI: java -jar "+PROGRAM_NAME+" adresar_trenovaci adresar_testovaci vektorovaci_algoritmus(0-2) klasifikacni_algoritmus(0-1) soubor_modelu");
        System.out.println();
        System.out.println("Vektorovací algoritmy: ");
        System.out.println(" 0 - Histogram");
        System.out.println(" 1 - Proporce");
        System.out.println(" 2 - Kexik");
        System.out.println();
        System.out.println("Klasifikační algoritmy: ");
        System.out.println(" 0 - Klasifikátor s nejmenší vzdáleností");
        System.out.println(" 1 - Naivní Bayes");
    }

    public static void main(String[] args) {
        switch (args.length) {
            case 1: showGui(args[0]); break;
            case 5: {
                int v = Integer.valueOf(args[2]);
                int c = Integer.valueOf(args[3]);
                if (c<0||v<0||c>=Classification.COUNT||v>=Vectoring.COUNT) {
                    showHelp();
                } else {
                    Cli.run(args[0],args[1],v,c,args[4]);
                }
            } break;
            default:  showHelp();
        }
    }
}
