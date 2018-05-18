package lab2obligatoriu;

import java.util.Vector;

class Profesor {
    private String numeProf;
    private String emailProf;
    public Vector<Proiect> listaProiecte;
    
    public Profesor(String nume, String email){
        this.numeProf = nume;
        this.emailProf = email;
    }
    
}
