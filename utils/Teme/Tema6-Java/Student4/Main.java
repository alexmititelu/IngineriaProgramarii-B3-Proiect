package lab;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Main {
    public static void main(String[] args) {

        DrawingFrame frame = new DrawingFrame();

        JFrame a = new JFrame();

        a.setSize(500, 500);
        a.setVisible(true);

        Canvas canvas = new Canvas(a);

        a.add(canvas);

        canvas.setSize(500, 500);
        canvas.setBackground(Color.white);

        JPanel p = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;

                Shape cap = new Ellipse2D.Double(145, 150, 100, 100);


                g2.draw(cap);
            }
        };



        p.setVisible(true);

        a.getContentPane().add(p);
    }
}
