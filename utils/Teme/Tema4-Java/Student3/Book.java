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
import java.lang.RuntimeException;
import java.lang.Exception;
import java.io.*;
public class Book extends Item {
    private String autor;
    
    public Book(String name, String link, Integer an, String autor) {
        
        this.autor = autor;
        this.name=name;
        this.link=link;
        this.an=an;
          
}
    
    public String toString(){
        return name;
    }
    
    
}