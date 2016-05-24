package cz.zcu.qwerty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class DrawingPanel extends JPanel {
    public static final int WIDTH = 128;
    public static final int HEIGHT = 128;

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
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseDown = false;
                newPoint = e.getPoint();
                repaint();
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
            g2.setStroke(new BasicStroke(8,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
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

    public int[][] getPixMap() {
        int [][] pixMap = new int[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Color c = new Color(drawingImage.getRGB(i,j));
                pixMap[i][j]  = (int)(
                        c.getRed() * 0.299 +
                        c.getGreen() * 0.587 +
                        c.getBlue() *0.114);
                // na sedivou
            }
        }
        return pixMap;
    }
    public void setPixMap(int [][] pixmap) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                int c = pixmap[i][j];
                drawingImage.setRGB(i,j,(new Color(c,c,c)).getRGB());
            }
        }
        repaint();
    }
}

