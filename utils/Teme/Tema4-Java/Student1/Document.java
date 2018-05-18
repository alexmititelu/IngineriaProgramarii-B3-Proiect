package com.company;

import java.util.ArrayList;
import java.util.List;

public abstract class Document {
    private String title;
    private String path;
    private List<String> autors;
    private int year;

    public Document(String title, String path, List<String> autors, int year) throws Exception{
        this.title = title;
        this.path = path;
        this.autors = new ArrayList<>();
        for(String contor:autors){
           this.autors.add(contor);
        }
        if(year > 2018){
            throw new Exception("Anul este prea mare");
        }
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public List<String> getAutors() {
        return autors;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Document{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", autors=" + autors +
                ", year=" + year +
                '}';
    }
}
