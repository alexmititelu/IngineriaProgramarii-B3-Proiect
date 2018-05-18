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
public class Main {
    public static void main(String[] args){
    Catalog catalog = new Catalog();
    catalog.add (new Book("The Art of Computer Programming", "d:\books\programming\tacp.ps", 1967, "Donald E. Knuth"));  
    catalog.add (new Article("Mastering the Game of Go without Human Knowledge", "d:\articles\AlphaGo.pdf", 2017, "David Silver", "Julian Schrittwieser", "Karen Simonyan"));
    catalog.save("d:\catalog.dat");
  
    }
}
