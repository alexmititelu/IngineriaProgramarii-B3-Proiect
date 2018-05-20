package com.company;

import java.util.ArrayList;

public class Student extends Person {

    private ArrayList<Project> projectsPreference = new ArrayList<Project>();

    public Student(String name, String email) {
        super(name, email);
    }

    public void setPreferences(Project...projects){
        boolean gasit = false;
        for(Project counter: projects){
            gasit = false;
            for(int i=0;i<projectsPreference.size();i++){
                if(projectsPreference.get(i).getName().compareTo(counter.getName()) == 0){
                    System.out.println("Proiectul "+ counter.getName()+" exista deja in lista de preferinte a studentului "+ this.getName());
                    gasit = true;
                }
            }
            if(!gasit) {
                projectsPreference.add(counter);
            }
        }

    }

    public ArrayList<Project> getProjectsPreference() {
        return projectsPreference;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(this.toString().compareTo(obj.toString()) == 0){
            return true;
        }

        return false;
    }
}
