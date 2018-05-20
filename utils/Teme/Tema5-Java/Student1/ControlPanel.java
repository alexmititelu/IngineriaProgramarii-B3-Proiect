package com.company;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel{
    private final CatalogFrame frame;
    JButton loadBtn = new JButton("Load");
    JButton saveBtn = new JButton("Save");

    public ControlPanel(CatalogFrame frame){
        this.frame = frame;
        init();
        this.frame.add(this);
    }

    private void init(){
        add(loadBtn);
        add(saveBtn);
        setLayout(new GridLayout());
    }
}
