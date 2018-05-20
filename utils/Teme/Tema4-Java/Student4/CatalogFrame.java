package graphic;

import com.sun.deploy.panel.ControlPanel;

import javax.swing.*;

public class CatalogFrame extends JFrame {
    DocumentForm form;
    CatalogList list;
    ControlPanel control;

    public CatalogFrame() {
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