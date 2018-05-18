package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class Teacher {
    private String name;
    private String mail;

    private ArrayList<Student> vectProb;

    Teacher (String name, String mail)
    {
        this.name=name;
        this.mail=mail;
        this.vectProb=new ArrayList<Student>();
    }

    public Project createProject (String projectName, int capacity)
    {
        return new Project(projectName,capacity);
    }

    public void setPreferences (Student ... s)
    {
        Collections.addAll(vectProb, s);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public ArrayList<Student> getVectProb() {
        return vectProb;
    }

    public void setVectProb(ArrayList<Student> vectProb) {
        this.vectProb = vectProb;
    }

    public void afisare ()
    {
        System.out.println("profesorul "+ this.getName() + " are urmatorii studenti preferati: ");
        for(Student s : vectProb )
        {
            System.out.print(s.getName() + " ");
        }
        System.out.println();
    }
}
