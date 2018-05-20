package graphic;

import javax.swing.*;

public class DocumentForm extends JPanel {
    private final CatalogFrame frame;
    JLabel titleLabel = new JLabel("Title of the document");

    JSpinner yearField = new JSpinner(
            new SpinnerNumberModel(1950, 1900, 2017, 1));

    public DocumentForm(CatalogFrame frame) {
        this.frame = frame;
    }

    private void init() {
//        addButton.addActionListener();
    }
    private void addDocument() {
        try {
            frame.list.addDocument();
        }
        catch(Exception exception){

            System.out.println("Error: " + exception.getMessage());

        }
    }
}