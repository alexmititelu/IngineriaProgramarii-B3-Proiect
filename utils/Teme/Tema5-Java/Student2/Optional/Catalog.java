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
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Catalog implements Serializable {
    private ArrayList<Document> documents;

    public Catalog(){
        documents = new ArrayList<>();
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public void add(Document document){
        documents.add(document);
    }

    public void save(String path) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(this);
        fileOutputStream.close();
    }

    public void load(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = null;
        fileInputStream = new FileInputStream(path);

        ObjectInputStream objectInputStream = null;
        objectInputStream = new ObjectInputStream(fileInputStream);

        Catalog catalog = (Catalog) objectInputStream.readObject();
        this.setDocuments(catalog.getDocuments());

        objectInputStream.close();
        fileInputStream.close();
    }

    public static void open(String path) throws IOException {
        Desktop.getDesktop().open(new File(path));
    }

    public void list(){
        for(Document document : documents){
            System.out.print(document + "\n");
        }
    }
}

