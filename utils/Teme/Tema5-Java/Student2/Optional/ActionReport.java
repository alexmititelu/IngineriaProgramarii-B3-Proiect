/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Implementation;

/**
 *
 * @author Eduard
 */
import com.GUI.CatalogFrame;
import com.GUI.CatalogTable;

import javax.swing.*;

public class ActionReport implements ActionButton {
    private final CatalogFrame frame;

    public ActionReport(CatalogFrame frame){
        this.frame = frame;
    }

    public void executeAction() {
        String[] columns = new String[] { "TITLE", "YEAR", "PATH" };

        DefaultListModel defList = frame.getList().getModel();
        Object[][] data = new Object[defList.getSize()][3];

        for(int indexRow = 0; indexRow < defList.getSize(); indexRow ++){
            Document document = (Document)defList.getElementAt(indexRow);
            data[indexRow] = new Object[] { document.getTitle(), document.getYear(), document.getPath() };
        }

        new CatalogTable(columns, data, frame).setVisible(true);
    }
}
