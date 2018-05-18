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

public class ActionOpen implements ActionButton {
    private final CatalogFrame frame;
    private final String path;

    public ActionOpen(String path, CatalogFrame frame){
        this.frame = frame;
        this.path = path;
    }

    public void executeAction() throws IOException {
        Catalog.open(path);
    }
}
