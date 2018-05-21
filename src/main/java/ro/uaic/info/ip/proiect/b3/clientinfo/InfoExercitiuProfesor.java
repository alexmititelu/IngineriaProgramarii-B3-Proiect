package ro.uaic.info.ip.proiect.b3.clientinfo;

import java.util.ArrayList;

public class InfoExercitiuProfesor {
    private String nume;
    private String enunt;
    private String extensie;
    private ArrayList<StudentNenotat> studentiNenotati;
    private ArrayList<StudentNotat> studentiNotati;
    private ArrayList<TemaPlagiata> temePlagiate;

    public InfoExercitiuProfesor(String nume, String enunt, String extensie, ArrayList<StudentNenotat> studentiNenotati, ArrayList<StudentNotat> studentiNotati, ArrayList<TemaPlagiata> temePlagiate) {
        this.nume = nume;
        this.enunt = enunt;
        this.extensie = extensie;
        this.studentiNenotati = studentiNenotati;
        this.studentiNotati = studentiNotati;
        this.temePlagiate = temePlagiate;
    }

    public String getNume() {

        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEnunt() {
        return enunt;
    }

    public void setEnunt(String enunt) {
        this.enunt = enunt;
    }

    public String getExtensie() {
        return extensie;
    }

    public void setExtensie(String extensie) {
        this.extensie = extensie;
    }

    public ArrayList<StudentNenotat> getStudentiNenotati() {
        return studentiNenotati;
    }

    public void setStudentiNenotati(ArrayList<StudentNenotat> studentiNenotati) {
        this.studentiNenotati = studentiNenotati;
    }

    public ArrayList<StudentNotat> getStudentiNotati() {
        return studentiNotati;
    }

    public void setStudentiNotati(ArrayList<StudentNotat> studentiNotati) {
        this.studentiNotati = studentiNotati;
    }

    public ArrayList<TemaPlagiata> getTemePlagiate() {
        return temePlagiate;
    }

    public void setTemePlagiate(ArrayList<TemaPlagiata> temePlagiate) {
        this.temePlagiate = temePlagiate;
    }
}
