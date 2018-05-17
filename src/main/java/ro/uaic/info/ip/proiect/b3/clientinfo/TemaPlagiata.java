package ro.uaic.info.ip.proiect.b3.clientinfo;

public class TemaPlagiata {
    private String username1;
    private String nume1;
    private String prenume1;
    private String username2;
    private String nume2;
    private String prenume2;
    private int procentPlagiarism;

    public TemaPlagiata(String username1, String nume1, String prenume1, String username2, String nume2, String prenume2, int procentPlagiarism) {
        this.username1 = username1;
        this.nume1 = nume1;
        this.prenume1 = prenume1;
        this.username2 = username2;
        this.nume2 = nume2;
        this.prenume2 = prenume2;
        this.procentPlagiarism = procentPlagiarism;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getNume1() {
        return nume1;
    }

    public void setNume1(String nume1) {
        this.nume1 = nume1;
    }

    public String getPrenume1() {
        return prenume1;
    }

    public void setPrenume1(String prenume1) {
        this.prenume1 = prenume1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public String getNume2() {
        return nume2;
    }

    public void setNume2(String nume2) {
        this.nume2 = nume2;
    }

    public String getPrenume2() {
        return prenume2;
    }

    public void setPrenume2(String prenume2) {
        this.prenume2 = prenume2;
    }

    public int getProcentPlagiarism() {
        return procentPlagiarism;
    }

    public void setProcentPlagiarism(int procentPlagiarism) {
        this.procentPlagiarism = procentPlagiarism;
    }
}
