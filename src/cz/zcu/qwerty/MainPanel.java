package cz.zcu.qwerty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainPanel extends JPanel {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;

    private static DrawingPanel drawingPanel;

    private JButton reset_button, start_button;

    int[][] proportionsEtalons = new int[10][];
    int[][] histogramEtalons = new int[10][];
    int[][] kexikEtalons = new int[10][];


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

        reset_button = new JButton("RESET");
        reset_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetClick();
            }
        });
        this.add(reset_button);
        start_button = new JButton("GO");
        start_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startClick();
            }
        });
        this.add(start_button);

        makeEtalons();
    }

    private void makeEtalons() {
        int [][] pom;
        for (int i = 0; i < 10; i++) {
            pom = Preprocessing.loadPPM(Main.class.getResourceAsStream("res/"+i+".ppm"));
            double center[] = Preprocessing.centerOfGravity(pom);
            pom = Preprocessing.shift(pom,(DrawingPanel.WIDTH/2)-(int)center[0],(DrawingPanel.HEIGHT/2)-(int)center[1]);
            proportionsEtalons[i] = Vectoring.proportions(pom);
            histogramEtalons[i] = Vectoring.histogram(pom);
            kexikEtalons[i] = Vectoring.kexik(pom);
        }
    }

    private void startClick() {

        int [][] input= drawingPanel.getPixMap();
        double center[] = Preprocessing.centerOfGravity(input);
        input = Preprocessing.shift(input,(DrawingPanel.WIDTH/2)-(int)center[0],(DrawingPanel.HEIGHT/2)-(int)center[1]);


        long sum;
        int lowest= -1;
        long lowest_sum = Long.MAX_VALUE;

        int[] proportions = Vectoring.proportions(input);

        for (int i = 0; i < 10; i++) {
            sum = 0;
            for (int u = 0; u < proportions.length; u++) {
                sum += Math.abs(proportionsEtalons[i][u] - proportions[u]);
            }
            System.out.println("sum "+i+" "+sum);

            if (sum<lowest_sum) {
                lowest = i;
                lowest_sum=sum;
            }
        }

        System.out.println();

        input = Preprocessing.loadPPM(Main.class.getResourceAsStream("res/"+lowest+".ppm"));
        drawingPanel.setPixMap(input);
        /*
        System.out.println(Arrays.toString(Vectoring.histogram(neco,127)));
        System.out.println(Arrays.toString(Vectoring.proportions(neco)));
        System.out.println(Arrays.toString(Preprocessing.centerOfGravity(neco)));

        double mass[] = Preprocessing.centerOfGravity(neco);
        neco = Preprocessing.shift(neco,(DrawingPanel.WIDTH/2)-(int)mass[0],(DrawingPanel.HEIGHT/2)-(int)mass[1]);

        System.out.println(Arrays.toString(Vectoring.histogram(neco,127)));
        System.out.println(Arrays.toString(Vectoring.proportions(neco)));
        System.out.println(Arrays.toString(Preprocessing.centerOfGravity(neco)));
        drawingPanel.setPixMap(neco);
        neco = Preprocessing.loadPPM(Main.class.getResourceAsStream("res/1.ppm"));
        drawingPanel.setPixMap(neco);
        */

    }

    private void resetClick() {
        drawingPanel.resetImage();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
