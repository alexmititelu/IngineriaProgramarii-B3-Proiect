package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DocumentForm extends JPanel implements ActionListener{
    private  final CatalogFrame frame ;
    JLabel titleLabel = new JLabel("Title of the document");
    JLabel pathLabel = new JLabel("Path int the local file system");

    JTextField documentTextField = new JTextField("Mastering the Game of Go without Human Knowledge", 50);
    JTextField pathTextField = new JTextField("d:/article/AlphaGo.pdf",50);
    JSpinner yearField = new JSpinner(new SpinnerNumberModel(1950,1900,2017,1));
    JButton addToRepositori = new JButton("Add to Repositori");

    public DocumentForm (CatalogFrame frame){
        this.frame = frame;
        init();
        this.setLayout(new GridLayout(this.getComponentCount(),1));
        this.frame.add(this);
    }

    private void init(){
        this.add(titleLabel);
        addToRepositori.addActionListener(this);
        this.add(documentTextField);
        this.add(pathLabel);

        this.add(pathTextField);
        yearField.setLocation(2, 40);
        this.add(new JLabel("Publication year:"));
        this.add(yearField);
        this.add(addToRepositori);
        this.setBorder(BorderFactory.createTitledBorder("Add Document"));
    }

    private void addDocument(){
        frame.getList().addDocument(documentTextField.getText().concat(yearField.getValue().toString()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addToRepositori){
            addDocument();
        }
    }
}
