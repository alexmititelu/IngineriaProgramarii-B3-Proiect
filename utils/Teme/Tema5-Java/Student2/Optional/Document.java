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
import java.io.File;
import java.io.Serializable;

public class Document implements Serializable {
    private String title, path, year;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Document(String title, String path, String year) throws CustomException {
        if(title.matches("(\\s|\\t)*")){
            throw new CustomException("Title Is Empty");
        }
        this.title = title;

        File file = new File(path);
        if(file.exists() == false){
            throw new CustomException("Path Non Existent");
        }
        this.path = path;

        if(year.matches("[1-9][0-9]*") == false){
            throw new CustomException("Non Valid Year");
        }
        this.year = year;
    }

    @Override
    public String toString() {
        return title + ", " + year + ", [" + path +"]";
    }
}

