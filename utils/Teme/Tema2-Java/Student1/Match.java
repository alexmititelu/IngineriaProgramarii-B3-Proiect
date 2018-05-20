package com.company;

import javafx.util.Pair;

import java.util.ArrayList;

public class Match {
   private Problem problem;
   private ArrayList<Pair<Project, Student>> matches;

    public Match() {
        matches = new ArrayList<Pair<Project, Student>>();
    }

   public void print(){
       for(Pair<Project, Student> key: matches){
           System.out.println(key.getValue().getName()+" -> "+ key.getKey().getName());
       }
   }

   private void maximKeyForHash (String projectName, Teacher teacher,Student student){
        int maxIndexMatch = -1;

        for(int i=0;i< matches.size();i++){
            if(matches.get(i).getKey().getName().compareTo(projectName) == 0){
                if(teacher.findStudentIndex(matches.get(i).getValue()) > maxIndexMatch && teacher.findStudentIndex(matches.get(i).getValue()) != Integer.MAX_VALUE){
                    maxIndexMatch = i;
                }
            }
        }

        if(maxIndexMatch != -1){
            if(teacher.findStudentIndex(matches.get(maxIndexMatch).getValue()) > teacher.findStudentIndex(student) && teacher.findStudentIndex(matches.get(maxIndexMatch).getValue()) != Integer.MAX_VALUE && teacher.findStudentIndex(student) != Integer.MAX_VALUE) {
                Student student2 = matches.get(maxIndexMatch).getValue();
                matches.set(maxIndexMatch, new Pair<>(matches.get(maxIndexMatch).getKey(), student));
                match(student2);

            }
        }

   }

    public void makeMatches(){
        for(int i=0; i< this.problem.getStudents().size();i++){
            //System.out.println(this.problem.getStudents().get(i).getName());
            match(this.problem.getStudents().get(i));

        }
    }

    private void match(Student student){

        for(Project projectStudentIterator : student.getProjectsPreference()){
            for(Teacher teacherIterator: this.problem.getTeachers()){
                for(Project projectTeacherIterator : teacherIterator.getProjectsCreated()){
                    if(projectStudentIterator.getName().compareTo(projectTeacherIterator.getName()) == 0){
                        if(projectStudentIterator.getCapacity() > 0){
                            matches.add(new Pair<>(projectTeacherIterator, student));
                            projectTeacherIterator.setCapacity(projectTeacherIterator.getCapacity() -1);
                            return;
                        }
                        else{
                            maximKeyForHash(projectTeacherIterator.getName(), teacherIterator, student);

                        }
                    }
                }
            }
        }
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }
}
