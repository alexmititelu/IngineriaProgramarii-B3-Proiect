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
import javax.swing.*;
import java.awt.*;

public class CatalogFrame extends JFrame {
    private DocumentForm form;
    private CatalogList list;
    private ControlPanel control;
    private CatalogTable catalogTable;

    public CatalogFrame() {
        super("Visual Document Manager");
        init();
    }

    public CatalogList getList() {
        return list;
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        form = new DocumentForm(this);
        add(form, BorderLayout.NORTH);

        list = new CatalogList();
        add(new JScrollPane(list), BorderLayout.CENTER);

        control = new ControlPanel(this);
        add(control, BorderLayout.SOUTH);

        pack();
        this.setLocationRelativeTo(null);
    }
}
