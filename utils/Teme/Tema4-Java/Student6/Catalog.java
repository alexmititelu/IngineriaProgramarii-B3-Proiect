package com.company;

import sun.security.krb5.internal.crypto.Des;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Catalog implements Serializable{
    private ArrayList<Document> documents;

    public Catalog() {
        documents = new ArrayList<>();
    }

    public void add (Document document){
        documents.add(document);
    }

    public void list (){
        for(Document contor:documents){
            System.out.println("Item: "+ contor.getTitle());
        }
    }

    public void open(String path){
        if(Desktop.isDesktopSupported()){
            Desktop desktop  = Desktop.getDesktop();
            try {
                desktop.open(new File(path));
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void save(String path){
        try {
            FileOutputStream file = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream( file);
            out.writeObject(this.toString());
            out.close();
            file.close();
        }catch (Exception e){
            System.out.println("Serialization exception is caught. -> "+ e.getMessage());
        }
    }

    public static Catalog load(String path){
        Catalog object1 = null;

        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            object1 = (Catalog)in.readObject();

            in.close();
            file.close();
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }
        return object1;

    }

    @Override
    public String toString() {
        return "Catalog{" +
                "documents=" + documents +
                '}';
    }
}
