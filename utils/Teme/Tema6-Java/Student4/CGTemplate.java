package lab;

import java.awt.*;       // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import javax.swing.*;    // Using Swing's components and containers

/** Custom Drawing Code Template */
// A Swing application extends javax.swing.JFrame
public class CGTemplate extends JFrame {
    // Define constants
    public static final int CANVAS_WIDTH  = 600;
    public static final int CANVAS_HEIGHT = 600;

    // Declare an instance of the drawing canvas,
    // which is an inner class called DrawCanvas extending javax.swing.JPanel.
    private DrawCanvas canvas;

    // Constructor to set up the GUI components and event handlers
    public CGTemplate() {
        canvas = new DrawCanvas();    // Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Set the Drawing JPanel as the JFrame's content-pane
        Container cp = getContentPane();
        cp.add(canvas);
        // or "setContentPane(canvas);"

        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();              // Either pack() the components; or setSize()
        setTitle("Lab6");  // "super" JFrame sets the title
        setVisible(true);    // "super" JFrame show
    }

    /**
     * Define inner class DrawCanvas, which is a JPanel used for custom drawing.
     */
    private class DrawCanvas extends JPanel {
        // Override paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);     // paint parent's background
            setBackground(Color.BLACK);  // set background color for this JPanel

            // Your custom painting codes. For example,
            // Drawing primitive shapes
            g.setColor(Color.white);    // set the drawing color
            g.drawOval(150, 180, 300, 300);

            g.fillOval(230, 250, 30, 30);
            g.fillOval(340, 250, 30, 30);

            g.fillOval(290, 310, 30, 40);

            g.drawLine(240, 310, 280, 320);
            g.drawLine(240, 330, 280, 330);
            g.drawLine(240, 350, 280, 340);


            g.drawLine(330, 320, 370, 310);
            g.drawLine(330, 330, 370, 330);
            g.drawLine(330, 340, 370, 350);


            g.drawOval(275, 400, 60, 10);

            g.drawLine(150, 140, 200, 180);
            g.drawLine(150, 140, 150, 220);

            g.drawLine(400, 180, 450, 140);
            g.drawLine(450, 140, 450, 220);


            g.drawRect(250, 480, 100, 200);

            g.drawLine(180, 480, 250, 520);
            g.drawLine(350, 520, 420, 480);

            g.drawLine(180, 720, 250, 680);
            g.drawLine(420, 720, 350, 680);

            g.drawLine(300, 680, 300, 820);
        }
    }

    // The entry main method
    public static void main(String[] args) {
        // Run the GUI codes on the Event-Dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CGTemplate(); // Let the constructor do the job
            }
        });
    }
}