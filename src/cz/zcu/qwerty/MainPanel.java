package cz.zcu.qwerty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;


public class MainPanel extends JPanel {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;

    private static DrawingPanel drawingPanel;

    private JButton reset_button, start_button;


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
    }

    private void startClick() {
       // System.out.println("neco");
        int [][] neco= drawingPanel.getPixMap();
        System.out.println(Arrays.toString(Vectoring.histogram(neco,127)));
        System.out.println(Arrays.toString(Vectoring.proportions(neco)));
        System.out.println(Arrays.toString(Preprocessing.centerOfGravity(neco)));

        double mass[] = Preprocessing.centerOfGravity(neco);
        neco = Preprocessing.shift(neco,(DrawingPanel.WIDTH/2)-(int)mass[0],(DrawingPanel.HEIGHT/2)-(int)mass[1]);

        System.out.println(Arrays.toString(Vectoring.histogram(neco,127)));
        System.out.println(Arrays.toString(Vectoring.proportions(neco)));
        System.out.println(Arrays.toString(Preprocessing.centerOfGravity(neco)));
        drawingPanel.setPixMap(neco);

    }

    private void resetClick() {
        drawingPanel.resetImage();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
