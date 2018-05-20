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

public class ActionLoad implements ActionButton {
    private final CatalogFrame frame;
    private final String path;

    public ActionLoad(String path, CatalogFrame frame){
        this.frame = frame;
        this.path = path;
    }

    public void executeAction() throws IOException, ClassNotFoundException {
        Catalog catalog = new Catalog();
        catalog.load(path);

        frame.getList().getModel().clear();
        for(Document document : catalog.getDocuments()){
            frame.getList().addDocument(document);
        }
    }
}
