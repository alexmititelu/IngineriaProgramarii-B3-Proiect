package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class Problem {
    private ArrayList<Student> vectStud = new ArrayList<Student>();
    private ArrayList<Teacher> vectTeach = new ArrayList<Teacher>();


    public void setStudents (Student ... s)
    {
        Collections.addAll(vectStud, s);
    }

    public void setTeachers (Teacher ... t)
    {
        Collections.addAll(vectTeach, t);
    }

    @Override
    public String toString() {
        return "OverrideSucces";
    }
}
