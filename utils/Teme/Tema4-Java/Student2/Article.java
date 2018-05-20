package com.company;
import java.lang.Exception;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;
public class Article extends Item {
    private ArrayList<String> autor;
    
    
    public Article(String name, String link, Integer an, String... autor) {
        this.autor = new ArrayList<String>();
        Collections.addAll(this.autor, autor);
        this.name = name;
        this.link = link;
        this.an = an;
    }
    
    public String toString(){
        return name;
    }
}
