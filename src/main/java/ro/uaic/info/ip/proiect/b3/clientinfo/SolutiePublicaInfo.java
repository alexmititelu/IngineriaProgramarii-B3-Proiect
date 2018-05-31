package ro.uaic.info.ip.proiect.b3.clientinfo;

public class SolutiePublicaInfo {
    private String numeStudent;
    private String prenumeStudent;
    private String usernameStudent;

    public SolutiePublicaInfo(String numeStudent, String prenumeStudent, String usernameStudent) {
        this.numeStudent = numeStudent;
        this.prenumeStudent = prenumeStudent;
        this.usernameStudent = usernameStudent;
    }

    public String getNumeStudent() {
        return numeStudent;
    }

    public void setNumeStudent(String numeStudent) {
        this.numeStudent = numeStudent;
    }

    public String getPrenumeStudent() {
        return prenumeStudent;
    }

    public void setPrenumeStudent(String prenumeStudent) {
        this.prenumeStudent = prenumeStudent;
    }

    public String getUsernameStudent() {
        return usernameStudent;
    }

    public void setUsernameStudent(String usernameStudent) {
        this.usernameStudent = usernameStudent;
    }
}
