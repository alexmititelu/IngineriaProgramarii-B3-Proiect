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

import java.io.IOException;

public class ActionSave implements ActionButton {
    private final String path;
    private final CatalogFrame frame;

    public ActionSave(String path, CatalogFrame frame){
        this.frame = frame;
        this.path = path;
    }

    public void executeAction() throws IOException {
        Catalog catalog = new Catalog();

        for(int index = 0; index < frame.getList().getModel().getSize(); index ++){
            catalog.add((Document)frame.getList().getModel().getElementAt(index));
        }

        catalog.save(path);
    }
}
