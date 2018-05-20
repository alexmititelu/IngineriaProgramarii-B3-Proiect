/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.catalog;

/**
 *
 * @author Aurelia
 */
import java.lang.Exception;
import java.io.*;
public abstract class Item {
    protected String name;
    protected String link;
    protected Integer an;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getAn() {
        return an;
    }

    public void setAn(Integer an) {
        this.an = an;
    }

    public String toString(){
        return name;
    }
}