package lab2obligatoriu;

import java.util.Vector;

class Student {
     private String numeStud;
    private String emailStud;
    public Vector<Proiect> listaProiect;
    
    public Student(String nume, String email){
        this.numeStud = nume;
        this.emailStud = email;
    }
    
}
