package ro.uaic.info.ip.proiect.b3.database.objects;

import java.io.Serializable;

public class Materie implements Serializable{


    private String nume;
    private Integer semestru;
    private Integer an;


    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getSemestru() {
        return semestru;
    }

    public void setSemestru(Integer semestru) {
        this.semestru = semestru;
    }

    public Integer getAn() {
        return an;
    }

    public void setAn(Integer an) {
        this.an = an;
    }
}
