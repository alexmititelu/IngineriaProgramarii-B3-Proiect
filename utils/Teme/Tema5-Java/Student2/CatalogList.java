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
import com.Implementation.Document;
import javax.swing.*;

public class CatalogList extends JList {
    DefaultListModel model = new DefaultListModel<>();

    public DefaultListModel getModel(){
        return model;
    }

    public CatalogList() {
        String title = "<html><i><font color='blue'>" + "Catalog Documents" + "</font></i></hmtl>";
        this.setBorder(BorderFactory.createTitledBorder(title));
        this.setModel(model);
    }
    public void addDocument(Document item) {
        model.addElement(item);
    }
}
