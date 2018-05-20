package com.company;

import javax.swing.*;
import java.awt.*;

public class CatalogFrame extends JFrame {
    private DocumentForm form;
    private CatalogList list = new CatalogList();
    private ControlPanel control;


    public CatalogFrame(){
        super("Visual Document Manager");
        init();
        pack();
    }

    public DocumentForm getForm() {
        return form;
    }

    public CatalogList getList() {
        return list;
    }

    public ControlPanel getControl() {
        return control;
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(10000, 800);
        form = new DocumentForm(this);
        add(list);
        control = new ControlPanel(this);

       // add(new CatalogList());
        this.setLayout(new BoxLayout(this.getContentPane(), 1));




    }
}
