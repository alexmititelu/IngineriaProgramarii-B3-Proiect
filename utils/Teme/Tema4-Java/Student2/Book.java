package com.company;
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
