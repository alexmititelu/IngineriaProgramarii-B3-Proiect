package com.company;

import com.sun.xml.internal.fastinfoset.util.StringArray;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Catalog catalog = new Catalog();
        try {
            catalog.add(new Book("dsad", "d:\\books\\programming\\tacp.ps", 1967, "Donald E. Knuth"));
            catalog.add(new Article("Mastering the Game of Go without Human Knowledge", "d:\\articles\\AlphaGo.pdf", 2017, "David Silver", "Julian Schrittwieser", "Karen Simonyan"));
            catalog.add(new Manual("Cookbook", "d:\\stuff\\cookbook.doc"));
        }catch (Exception e){
            System.out.println("InputError");
        }
        catalog.save("E://javalab//Lab4//src//com//company//file");
        catalog.open("E://javalab//Lab4//src//com//company//file");
        //...
        catalog = Catalog.load("E://javalab//Lab4//src//com//company//file");
        catalog.list();
    }
}
