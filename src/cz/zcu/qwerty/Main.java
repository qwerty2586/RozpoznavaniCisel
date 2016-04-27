package cz.zcu.qwerty;

import javax.swing.*;
import java.awt.*;

public class Main {
    static JFrame frame;
    public  static void showGui() {
        frame = new JFrame();
        MainPanel mainPanel = new MainPanel();
        frame.add(mainPanel);
        //frame.setPreferredSize(new Dimension(MainPanel.WIDTH,MainPanel.HEIGHT));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        showGui();
    }
}
