package com.company;
import java.lang.Exception;
import java.io.*;
public abstract class Item implements Serializable {
    protected String name;
    protected String link;
    protected Integer an;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getAn() {
        return an;
    }

    public void setAn(Integer an) {
        this.an = an;
    }

    public String toString(){
        return name;
    }
}
