/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import static javafx.scene.input.KeyCode.T;
import java.util.*;
import static java.util.Collections.list;

/**
 *
 * @author Eduard
 */
public class AssetManager {
    
    ArrayList<Item> list;
    ArrayList<Object> lista;
    ArrayList<Object> correctList;
    
    public AssetManager() {
        this.list=new ArrayList<Item>();
        this.lista=new ArrayList<Object>();
        this.correctList=new ArrayList<Object>();
    }
    
    public ArrayList<Item> getList(){
        return list;
    }
    
    public void add(Item ... i ){
        Collections.addAll(list, i);
    }
    
    public void print()
    {
        for(Item o : list)
            System.out.println(o.toString());
    }
    
    
    /*Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.name.compareTo(o2.name);
            }
        });
     */
    
    }
    
   
    
    
    
    
    
    
    

