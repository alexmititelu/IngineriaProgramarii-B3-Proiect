package ro.uaic.info.ip.proiect.b3.clientinfo;

import java.util.Date;

public class StudentNotat extends StudentNenotat {
    private int nota;

    public StudentNotat(String username, String nume, String prenume, Date dataUpload, int nota) {
        super(username, nume, prenume, dataUpload);
        this.nota = nota;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
