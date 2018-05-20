package graphic;

import javax.swing.*;

public class ControlPanel extends JPanel {
    private final CatalogFrame frame;
    JButton loadBtn = new JButton("Load");
    public ControlPanel(CatalogFrame frame) {
        this.frame = frame;
        try {
            init();
        }
        catch(Exception exception){

            System.out.println("Error: " + exception.getMessage());

        }
    }
    private void init() {
        try {
            add(loadBtn);
        }
        catch(Exception exception){

            System.out.println("Error: " + exception.getMessage());

        }
    }
}
