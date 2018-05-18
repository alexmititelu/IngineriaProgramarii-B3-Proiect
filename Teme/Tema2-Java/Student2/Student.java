package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class Student
{
    private String name;
    private String mail;
    private ArrayList<Project> vectProb;

    Student(String name, String mail)
    {
        this.name=name;
        this.mail=mail;
        this.vectProb=new ArrayList<Project>();
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public ArrayList<Project> getVectProb() {
        return vectProb;
    }

    public void setPreferences (Project ... p)
    {
        Collections.addAll(vectProb, p);
    }

    @Override
    public String toString() {
        return name + " "+ mail + " " ;

    }
}
