package com.company;

import java.util.ArrayList;

public class Teacher extends Person {
    private ArrayList<Student> studentsPreference = new ArrayList<Student>();
    private ArrayList<Project> projectsCreated = new ArrayList<Project>();


    public Teacher(String name, String email) {
        super(name, email);
    }

    public int findStudentIndex(Student student){
        for(int i=0;i<studentsPreference.size();i++){
            if(studentsPreference.get(i).getName().compareTo(student.getName()) == 0){
                return i;
            }
        }

        return Integer.MAX_VALUE;
    }

    public Project createProject(String name, int capacity){

        for(int i=0; i<projectsCreated.size();i++){
            if(projectsCreated.get(i).getName().compareTo(name) == 0){
                return projectsCreated.get(i);
            }
        }

        Project project = new Project();
        project.setCapacity(capacity);
        project.setName(name);

        projectsCreated.add(project);
        return project;
    }

    public ArrayList<Project> getProjectsCreated() {
        return projectsCreated;
    }

    public void setPreferences(Student...students){
        boolean gasit = false;
        for(Student counter: students){
            gasit = false;
            for(int i=0;i<studentsPreference.size();i++){

                if(studentsPreference.get(i).getName().compareTo(counter.getName()) == 0){
                    System.out.println("Studentul "+ counter.getName()+" exista deja la profesorul "+ this.getName());
                    gasit = true;
                }
            }

            if(!gasit){
                System.out.println("Studentul "+ counter.getName()+" a fost inscris la proful "+ this.getName());
                studentsPreference.add(counter);
            }


        }

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
