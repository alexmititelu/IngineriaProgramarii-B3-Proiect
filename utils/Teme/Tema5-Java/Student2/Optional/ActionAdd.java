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

public class ActionAdd implements ActionButton {
    private final CatalogFrame frame;
    private final Document document;

    public ActionAdd(Document document, CatalogFrame frame){
        this.frame = frame;
        this.document = document;
    }

    public void executeAction() {
        frame.getList().addDocument(document);
    }
}
