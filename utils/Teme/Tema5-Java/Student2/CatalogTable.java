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

public class CatalogTable extends JFrame {
    private final CatalogFrame frame;
    JTable table;

    public CatalogTable(String[] columns, Object data[][], CatalogFrame frame){
        this.frame = frame;
        init(columns, data);
    }

    private void init(String[] columns, Object data[][]){
        table = new JTable(data, columns){
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        this.add(new JScrollPane(table));

        this.setTitle("Catalog Report");
        this.pack();
    }
}
