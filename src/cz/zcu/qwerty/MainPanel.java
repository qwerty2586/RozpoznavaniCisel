package cz.zcu.qwerty;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;


public class MainPanel extends JPanel {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 200;

    private static DrawingPanel drawingPanel;

    private JButton reset_button, start_button;

    private JRadioButton[] vector_button = new JRadioButton[3];
    private JRadioButton[] classifier_button = new JRadioButton[2];

    private JLabel result_label;

    Model model;


    public MainPanel(Model model) {
        super();
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setLayout(new FlowLayout(FlowLayout.LEADING));

        this.model = model;

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
        start_button = new JButton("START");
        start_button.addActionListener(e -> startClick());
        this.add(start_button);

        result_label = new JLabel("");
        this.add(result_label);

    }

    private void initRadioButtons() {
        JPanel panel = new JPanel();
        ButtonGroup group = new ButtonGroup();
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        vector_button[0] = new JRadioButton("histogram");
        vector_button[1] = new JRadioButton("proporce");
        vector_button[2] = new JRadioButton("průsečíky");
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
            etalons = model.histogramEtalons;
        } else if (vector_button[1].isSelected()) {
            sample = Vectoring.proportions(input);
            etalons = model.proportionsEtalons;
        } else  {
            sample = Vectoring.kexik(input);
            etalons = model.kexikEtalons;
        }
        if (classifier_button[0].isSelected()) {
            int lowest = Classification.lowestDistance(Classification.MANHATTAN, sample, etalons);
            result_label.setText("nejblizsi odhad, etalon: " + lowest + " cislice: " + model.resultMap[lowest]);
        } else {
            int biggest;
            if (vector_button[2].isSelected()) biggest = Classification.naive_bayes(sample, etalons,model.resultMap);
            else biggest = Classification.naive_bayes(Classification.divide(sample), Classification.divide(etalons),model.resultMap);
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
