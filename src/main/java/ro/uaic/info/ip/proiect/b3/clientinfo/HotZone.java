package ro.uaic.info.ip.proiect.b3.clientinfo;

public class HotZone {
    private int student1[];
    private int student2[];
    private int procent;

    public HotZone(int[] student1, int[] student2, int procent) {
        this.student1 = student1;
        this.student2 = student2;
        this.procent = procent;
    }

    public int[] getStudent1() {
        return student1;
    }

    public void setStudent1(int[] student1) {
        this.student1 = student1;
    }

    public int[] getStudent2() {
        return student2;
    }

    public void setStudent2(int[] student2) {
        this.student2 = student2;
    }

    public int getProcent() {
        return procent;
    }

    public void setProcent(int procent) {
        this.procent = procent;
    }
}
