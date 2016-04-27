package cz.zcu.qwerty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;


public class MainPanel extends JPanel {

    public static final int WIDTH = 200;
    public static final int HEIGHT = 200;

    static JPanel drawingPanel;
    boolean mouseDown;
    Point lastPoint,newPoint;
    BufferedImage drawingImage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);;


    public MainPanel() {
        super();
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());
        initDrawingPanel();

        this.add(drawingPanel,BorderLayout.PAGE_START);
        this.repaint();


    }

    private void initDrawingPanel() {


        Graphics2D g2 =  drawingImage.createGraphics();
        g2.setBackground(Color.white);
        g2.dispose();

        drawingPanel = new JPanel(true) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (mouseDown) {
                    Graphics2D g2 = drawingImage.createGraphics();

                    g2.setColor(Color.black);
                    g2.setStroke(new BasicStroke(3));
                    g2.drawLine(lastPoint.x,lastPoint.y,newPoint.x,newPoint.y);
                    lastPoint = newPoint;

                }
                g.drawImage(drawingImage,0,0,null);
            }

            @Override
            public void paint(Graphics g) {
                super.paint(g);

            }
        }; // double buffered

        drawingPanel.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        drawingPanel.setBorder(BorderFactory.createTitledBorder("panel"));
        drawingPanel.setLayout(new FlowLayout());


        mouseDown = false;
        drawingPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseDown  =true;
                lastPoint = e.getPoint();

                System.out.print('d');
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseDown = false;
                newPoint = e.getPoint();
                drawingPanel.repaint();
                System.out.print('u');
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseDown = false;
            }
        });
        drawingPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                newPoint = e.getPoint();
                drawingPanel.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

    }



    @Override
    public void paint(Graphics g) {
        super.paint(g);



    }
}
