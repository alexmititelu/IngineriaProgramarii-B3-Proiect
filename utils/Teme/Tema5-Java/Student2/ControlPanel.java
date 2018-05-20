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
import com.Implementation.ActionLoad;
import com.Implementation.ActionOpen;
import com.Implementation.ActionReport;
import com.Implementation.ActionSave;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ControlPanel extends JPanel {
    private final CatalogFrame frame;

    JButton loadButton = new JButton("Load");
    JButton saveButton = new JButton("Save");
    JButton openButton = new JButton("Open");
    JButton reportButton = new JButton("Report");

    public ControlPanel(CatalogFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCatalog();
            }
        });
        add(loadButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCatalog();
            }
        });
        add(saveButton);

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        add(openButton);

        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportCatalog();
            }
        });
        add(reportButton);
    }

    private void loadCatalog(){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exception) {
                    exception.printStackTrace();
                    return;
                }

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                    try {
                        new ActionLoad(fileChooser.getSelectedFile().getPath(), frame).executeAction();
                        JOptionPane.showMessageDialog(frame, "Catalog Loaded");
                    } catch (IOException | ClassNotFoundException exception) {
                        JOptionPane.showMessageDialog(frame, exception.getMessage());
                    }
                }

            }
        });
    }

    private void saveCatalog(){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exception) {
                    exception.printStackTrace();
                    return;
                }

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

                    try {
                        new ActionSave(fileChooser.getSelectedFile().getPath(), frame).executeAction();
                        JOptionPane.showMessageDialog(frame, "Catalog Saved");
                    } catch (IOException exception) {
                        JOptionPane.showMessageDialog(frame, exception.getMessage());
                    }
                }

            }
        });
    }

    private void openFile(){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exception) {
                    exception.printStackTrace();
                    return;
                }

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                    try {
                        new ActionOpen(fileChooser.getSelectedFile().getPath(), frame).executeAction();
                    } catch (IOException exception) {
                        JOptionPane.showMessageDialog(frame, exception.getMessage());
                    }
                }

            }
        });
    }

    private void reportCatalog(){
        new ActionReport(frame).executeAction();
    }
}
