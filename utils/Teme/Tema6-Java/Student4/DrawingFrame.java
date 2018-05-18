package lab;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class DrawingFrame extends JFrame {
    public DrawingFrame() {
        super("Lab 5");
        try {
            init();

        }
        catch(Exception exception){

            System.out.println("Error: " + exception.getMessage());

        }
    }

    private void init() {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
        catch(Exception exception){

            System.out.println("Error: " + exception.getMessage());

        }
    }
}
