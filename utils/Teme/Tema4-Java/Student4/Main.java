package info;

import graphic.CatalogFrame;
import graphic.CatalogList;
import graphic.ControlPanel;
import graphic.DocumentForm;

import javax.swing.*;
import java.awt.*;

/**
 * Clasa Main cu toate datele
 */
public class Main {
    /**
     * functia main
     * @param args argumentele citite de la tastatura
     * @throws ExceptionCustom exceptiile aferentes
     */
    static public void main(String[] args) throws ExceptionCustom {

        /**
         * initializare prototipuri
         */
        CatalogFrame a = new CatalogFrame();
        CatalogList b = new CatalogList();
        ControlPanel c = new ControlPanel(a);
        DocumentForm d = new DocumentForm(a);

        /**
         * label first
         */
        JLabel label = new JLabel("Add document");
        label.setVisible(true);

        /**
         * label second
         */
        JLabel label2 = new JLabel("Title of the document");
        label2.setVisible(true);

        /**
         * label third
         */
        JLabel label3 = new JLabel("Path in the local file system");
        label3.setVisible(true);

        /**
         * label forth
         */
        JLabel label4 = new JLabel("Publication Year");
        label4.setVisible(true);

        /**
         * label fifth
         */
        JLabel label5 = new JLabel("Catalog Documents");
        label5.setVisible(true);

        /**
         * text field
         */
        JTextField textField = new JTextField(20);
        textField.setVisible(true);
        /**
         * text field
         */
        JTextField textField2 = new JTextField(20);
        textField2.setVisible(true);

        /**
         * pineer number
         */
        SpinnerNumberModel m_numberSpinnerModel;
        m_numberSpinnerModel = new SpinnerNumberModel(2018, 1900, 2018, 1);
        JSpinner m_numberSpinner = new JSpinner(m_numberSpinnerModel);
        m_numberSpinner.setVisible(true);

        /**
         * btn first
         */
        JButton btn = new JButton("Add to repository");

        /**
         * text area
         */
        JTextArea textArea = new JTextArea(
                ""
        );
        textArea.setFont(new Font("Serif", Font.ITALIC, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        /**
         * buttons
         */
        JButton btn2 = new JButton("Load");
        JButton btn3 = new JButton("Save");
        JButton btn4 = new JButton("");

        /**
         * size canvas
         */
        a.setSize(500, 500);

        /**
         * add elements
         */
        a.add(label);
        a.add(label2);
        a.add(label3);
        a.add(label4);
        a.add(label5);
        a.add(btn);
        a.add(m_numberSpinner);
        a.add(textField);

        a.add(textField2);
        a.add(textArea);
        a.add(btn2);
        a.add(btn3);
        a.add(btn4);


        a.setVisible(true);

        /**
         * format size
         */
        textField2.setSize(300, 20);
        textField2.setLocation(0, 125);
        label.setSize(200,50);
        label2.setSize(200,100);
        label3.setSize(200, 50);
        label3.setLocation(0, 85);
        label4.setSize(200, 50);
        label4.setLocation(0, 145);

        label5.setSize(200, 50);
        label5.setLocation(0, 265);

        textField.setSize(300, 20);
        textField.setLocation(0, 65);


        btn.setBounds(0, 220, 150, 50);
        btn2.setBounds(100, 320, 150, 50);
        btn3.setBounds(280, 320, 150, 50);
        btn4.setBounds(280, 320, 150, 50);

        m_numberSpinner.setSize(150, 30);
        m_numberSpinner.setLocation(0, 180);

        textArea.setLocation(0, 200);



        Catalog catalog = new Catalog();
        catalog.add (new Book("The Art of Computer Programming", "tacp.ps", 1967, "Donald E. Knuth"));
        catalog.add (new Article("Mastering the Game of Go without Human Knowledge", "AlphaGo.pdf", 2017, "David Silver", "Julian Schrittwieser", "Karen Simonyan"));
        catalog.add (new Manual("Cookbook", "cookbook.doc"));
        catalog.save("catalog.dat");

        catalog = Catalog.load("catalog.dat");
        catalog.list();

    }
}
