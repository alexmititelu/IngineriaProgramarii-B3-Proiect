package graphic;

import javax.swing.*;

public class CatalogList extends JList {
    DefaultListModel model = new DefaultListModel<>();

    public CatalogList() {
        String title = "<html><i><font color='blue'>" +
                "Catalog Documents" + "</font></i></hmtl>";
        try {
            this.setBorder(BorderFactory.createTitledBorder(title));
            this.setModel(model);
        }
        catch(Exception exception){

            System.out.println("Error: " + exception.getMessage());

        }
    }
    public void addDocument(String item) {
        try {
            model.addElement(item);
        }
        catch(Exception exception){

            System.out.println("Error: " + exception.getMessage());

        }
    }

    public void addDocument() {

    }
}