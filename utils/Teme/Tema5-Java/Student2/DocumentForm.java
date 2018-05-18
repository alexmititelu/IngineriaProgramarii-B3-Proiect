/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GUI;

/**
 *
 * @author Eduard
 */
import com.Implementation.ActionAdd;
import com.Implementation.CustomException;
import com.Implementation.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DocumentForm extends JPanel {
    private final CatalogFrame frame;

    JLabel titleLabel = new JLabel("Title of the document");
    JTextField titleField = new JTextField();

    JLabel pathLabel = new JLabel("Path in the local file system");
    JTextField pathField = new JTextField();

    JLabel yearLabel = new JLabel("Publication Year");
    JSpinner yearField = new JSpinner(new SpinnerNumberModel(1950, 1900, 2017, 1));

    JButton addButton = new JButton("Add to repository");

    public DocumentForm(CatalogFrame frame){
        this.frame = frame;
        setLayout(new GridLayout(7, 1));
        setBorder(BorderFactory.createTitledBorder("Add document"));
        init();
    }

    private void init() {
        add(titleLabel);
        add(titleField);

        add(pathLabel);
        add(pathField);

        add(yearLabel);
        add(yearField);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDocument(titleField.getText(), pathField.getText(), yearField.getValue().toString());
            }
        });
        add(addButton);

    }
    private void addDocument(String title, String path, String year) {
        try {
            new ActionAdd(new Document(title, path, year), frame).executeAction();
        } catch (CustomException exception) {
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        }
    }
}
