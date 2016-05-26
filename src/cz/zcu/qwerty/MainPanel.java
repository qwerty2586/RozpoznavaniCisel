package cz.zcu.qwerty;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class MainPanel extends JPanel {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 200;

    private static DrawingPanel drawingPanel;

    private JButton reset_button, start_button;

    private JRadioButton[] vector_button = new JRadioButton[3];
    private JRadioButton[] classifier_button = new JRadioButton[2];

    private JLabel result_label;

    int[][] proportionsEtalons; // = new int[10][];
    int[][] histogramEtalons;   // = new int[10][];
    int[][] kexikEtalons;       // = new int[10][];

    int[] resultMap;
    String[] filenames;


    public MainPanel() {
        super();
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setLayout(new BorderLayout());
        setLayout(new FlowLayout(FlowLayout.LEADING));


        initItems();
        this.repaint();


    }

    private void initItems() {
        drawingPanel = new DrawingPanel();
        this.add(drawingPanel);

        initRadioButtons();

        reset_button = new JButton("RESET");
        reset_button.addActionListener(e -> resetClick());
        this.add(reset_button);
        start_button = new JButton("GO");
        start_button.addActionListener(e -> startClick());
        this.add(start_button);

        result_label = new JLabel("");
        this.add(result_label);

        try {
            makeEtalons("A13B0303P");
        } catch (IOException e) {
            System.out.println("Nepodarilo se nacist trenovaci data...");
            start_button.setEnabled(false);
            reset_button.setEnabled(false);

        }
    }

    private void initRadioButtons() {
        JPanel panel = new JPanel();
        ButtonGroup group = new ButtonGroup();
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        vector_button[0] = new JRadioButton("histogram");
        vector_button[1] = new JRadioButton("proportions");
        vector_button[2] = new JRadioButton("kexik");
        group.add(vector_button[0]);
        group.add(vector_button[1]);
        group.add(vector_button[2]);
        panel.add(vector_button[0]);
        panel.add(vector_button[1]);
        panel.add(vector_button[2]);
        vector_button[0].setSelected(true);
        TitledBorder title = BorderFactory.createTitledBorder("Tvorba příznaků");
        panel.setBorder(title);
        this.add(panel);


        panel = new JPanel();
        group = new ButtonGroup();
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        classifier_button[0] = new JRadioButton("mannhattan distance");
        classifier_button[1] = new JRadioButton("naive bayes");
        group.add(classifier_button[0]);
        group.add(classifier_button[1]);
        panel.add(classifier_button[0]);
        panel.add(classifier_button[1]);
        classifier_button[0].setSelected(true);
        title = BorderFactory.createTitledBorder("Klasifikátor");
        panel.setBorder(title);
        this.add(panel);


    }

    private void makeEtalons(String directory) throws IOException {

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

        resultMap = new int[total_length];

        histogramEtalons = new int[total_length][];
        proportionsEtalons = new int[total_length][];
        kexikEtalons = new int[total_length][];

        filenames = new String[total_length];

        int it = 0;

        for (int i = 0; i < 10; i++) {
            f = new File(directory+i);
            File[] files = f.listFiles();
            for (int j = 0; j < files.length; j++) { // tady uz pochazime soubory
                FileInputStream is = new FileInputStream(files[j]);
                int [][] pom = Preprocessing.loadPPM(is);
                double center[] = Preprocessing.centerOfGravity(pom);
                pom = Preprocessing.shift(pom,(DrawingPanel.WIDTH/2)-(int)center[0],(DrawingPanel.HEIGHT/2)-(int)center[1]);

                proportionsEtalons[it] = Vectoring.proportions(pom);
                histogramEtalons[it] = Vectoring.histogram(pom);
                kexikEtalons[it] = Vectoring.kexik(pom);
                resultMap[it] = i;
                filenames[it] = files[j].getPath();
                it++;

                is.close();
            }

        }









    }


    private void startClick() {

        int [][] input= drawingPanel.getPixMap();
        double center[] = Preprocessing.centerOfGravity(input);
        if (Double.isNaN(center[0])) {
            result_label.setText("Prazdny vstup");
            return;
        }

        input = Preprocessing.shift(input,(DrawingPanel.WIDTH/2)-(int)center[0],(DrawingPanel.HEIGHT/2)-(int)center[1]);

        int [] sample;
        int [][] etalons;


        if (vector_button[0].isSelected()) {
            sample = Vectoring.histogram(input);
            etalons = histogramEtalons;
        } else if (vector_button[1].isSelected()) {
            sample = Vectoring.proportions(input);
            etalons = proportionsEtalons;
        } else  {
            sample = Vectoring.kexik(input);
            etalons = kexikEtalons;
        }
        if (classifier_button[0].isSelected()) {
            int lowest = Classification.lowestDistance(Classification.MANHATTAN, sample, etalons);
/*
        input = Preprocessing.loadPPM(Main.class.getResourceAsStream("res/"+lowest+".ppm"));
        drawingPanel.setPixMap(input);
*/
            result_label.setText("nejblizsi odhad, etalon: " + filenames[lowest] + " cislice: " + resultMap[lowest]);
        } else {
            int biggest;
            if (vector_button[2].isSelected()) biggest = Classification.naive_bayes(sample, etalons,resultMap);
            else biggest = Classification.naive_bayes(Classification.divide(sample), Classification.divide(etalons),resultMap);
            result_label.setText("nejblizsi odhad cislice: " + biggest);
        }
    }

    private void resetClick() {
        drawingPanel.resetImage();
        result_label.setText("");
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
