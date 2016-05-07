package cz.zcu.qwerty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class DrawingPanel extends JPanel {
    private static final int WIDTH = 128;
    private static final int HEIGHT = 128;

    private boolean mouseDown;
    private Point lastPoint,newPoint;
    private BufferedImage drawingImage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);

    public DrawingPanel() {
        super(true);
        Graphics2D g2 =  drawingImage.createGraphics();
        g2.setBackground(Color.white);
        g2.clearRect(0, 0, WIDTH, HEIGHT);
        g2.dispose();

        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setLayout(new FlowLayout());

        mouseDown = false;
        addMouseListener(new MouseListener() {
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
                repaint();
                System.out.print('u');
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                newPoint = e.getPoint();
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mouseDown) {
            Graphics2D g2 = drawingImage.createGraphics();

            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(3,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
            g2.drawLine(lastPoint.x,lastPoint.y,newPoint.x,newPoint.y);
            lastPoint = newPoint;

        }
        g.drawImage(drawingImage,0,0,null);
    }

    public void resetImage() {
        Graphics2D g2 =  drawingImage.createGraphics();
        g2.setBackground(Color.white);
        g2.clearRect(0, 0, WIDTH, HEIGHT);
        g2.dispose();
        repaint();
    }
}

