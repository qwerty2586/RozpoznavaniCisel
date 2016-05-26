package cz.zcu.qwerty;

import javax.swing.*;

public class Main {
    private static JFrame frame;
    private static void showGui() {
        frame = new JFrame();
        MainPanel mainPanel = new MainPanel();
        frame.add(mainPanel);
        //frame.setPreferredSize(new Dimension(MainPanel.WIDTH,MainPanel.HEIGHT));
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Rozpoznavani rucne psanych cisel | Milan Hajzman | qwerty2@students.zcu.cz");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    public static final String PROGRAM_NAME = "A13B0303P.jar";

    public static void showHelp() {
        System.out.println("Spatne argumenty");
        System.out.println("GUI: java -jar "+PROGRAM_NAME+" model_file");
        System.out.println("Spatne argumenty");
    }

    public static void main(String[] args) {
        switch (args.length) {
            case 0: showGui(); break;
            case 5: {
                int v = Integer.valueOf(args[2])-1;
                int c = Integer.valueOf(args[3])-1;
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
