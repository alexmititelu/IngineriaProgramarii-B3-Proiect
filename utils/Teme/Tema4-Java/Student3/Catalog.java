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
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Exception;
public class Catalog implements Serializable {
    private ArrayList<Item> stuff;

    public void add(Item ... o ){
        Collections.addAll(stuff, o);
    }

        public void list()
        {
            for(Item i: stuff)
                System.out.println(i.getName());
        }

    public Catalog() {
        this.stuff = new ArrayList<Item>();

    }
    
    public void save(){
        try{
        FileOutputStream fileOut = new FileOutputStream("C:\\facultate\\anul 2\\PA\\Lab4\\src\\com\\company\\catalog\\catalog.txt");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        for(Item i : stuff){
            out.writeObject(i);
        }
        out.close();
        fileOut.close();
        System.out.println("Serialization complete");
        }catch(IOException i){
            System.out.println("Serialization failure");
        }
      
    }
    