package ro.uaic.info.ip.proiect.b3.clientinfo;

import java.util.Date;

public class StudentNenotat {
    private String username;
    private String nume;
    private String prenume;
    private Date dataUpload;

    public StudentNenotat(String username, String nume, String prenume, Date dataUpload) {
        this.username = username;
        this.nume = nume;
        this.prenume = prenume;
        this.dataUpload = dataUpload;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Date getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(Date dataUpload) {
        this.dataUpload = dataUpload;
    }
}
