package ro.uaic.info.ip.proiect.b3.clientinfo;

public class ExercitiuInfoStudent {
    private String nume;
    private String enunt;
    private String extensie;
    private boolean isUploaded;
    private String nota;

    public ExercitiuInfoStudent(String nume, String enunt, String extensie, boolean isUploaded, String nota) {
        this.nume = nume;
        this.enunt = enunt;
        this.extensie = extensie;
        this.isUploaded = isUploaded;
        this.nota = nota;
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

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
