package cz.zcu.qwerty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainPanel extends JPanel {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;

    private static DrawingPanel drawingPanel;

    private JButton reset_button;


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
        





    }

    private void resetClick() {
        drawingPanel.resetImage();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);



    }
}
