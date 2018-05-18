package com.company;

import java.util.ArrayList;

public class Problem {
    private ArrayList<Student> students ;
    private ArrayList<Teacher> teachers ;

    public Problem() {
        students = new ArrayList<Student>();
        teachers = new ArrayList<Teacher>();
    }

    public ArrayList<String> getParticipants(){
        ArrayList<String> participans = new ArrayList<String>();
        for(Teacher counter:teachers){
            participans.add(counter.getName());
        }

        for(Student counter:students){
            participans.add(counter.getName());
        }

        return participans;
    }

    public void setStudents(Student...students){
        boolean gasit;
        for(Student counter: students){
            gasit = false;
            for(int i=0;i<this.students.size();i++){
                if(this.students.get(i).equals(counter)){
                    System.out.println("Studentul "+ counter.getName()+ " exista deja in lista problemei.");
                    gasit = true;
                }
            }

            if(!gasit) {
                this.students.add(counter);
            }
        }
    }

    public void setTeachers (Teacher...teachers){
        boolean gasit;
        for(Teacher counter:teachers){
            gasit = false;
            for(int i=0;i<this.teachers.size();i++){
                if(this.teachers.get(i).equals(counter)){
                    System.out.println("Profesorul "+ counter.getName()+ " exista deja in lista problemei.");
                    gasit = true;
                    break;
                }
            }

            if(!gasit) {
                this.teachers.add(counter);
            }
        }
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        boolean gasit;
        for(Student counter: students){
            gasit = false;
            for(Student counter2: this.students){
                if(counter2.equals(counter)){
                    System.out.println("Studentul "+counter.getName()+" exista deja in lista problemei");
                    gasit = true;
                    return;
                }
            }

            if(!gasit){
                this.students.add(counter);
            }

        }


    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "students=" + students +
                ", teachers=" + teachers +
                '}'+'\n';
    }
}
