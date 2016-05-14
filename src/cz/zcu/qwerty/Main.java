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

    public static void main(String[] args) {
        showGui();
    }
}
