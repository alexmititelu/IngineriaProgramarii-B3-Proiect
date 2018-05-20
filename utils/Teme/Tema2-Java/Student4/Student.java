package problema;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Clasa Student
 * Memoreaza toate datele despre studenti
 */
public class Student extends Person {
    private String numeStudent;
    private String emailStudent;
    private ArrayList<Project> listaProiecte = new ArrayList<>();

    /**
     * Constructor student
     * @param nume numele studentului
     * @param email email-ul studentului
     */
    public Student(String nume, String email) {
        this.numeStudent = nume;
        this.emailStudent = email;
    }

    /**
     * Setare preferinte student
     * @param proj lista de proiecte
     */
    public void setPreferences(Project ... proj) {
        this.listaProiecte.addAll(Arrays.asList(proj));
    }

    public String getNumeStudent() {
        return numeStudent;
    }

    public String getEmailStudent() {
        return emailStudent;
    }

    public ArrayList<Project> getListaProiecte() {
        return listaProiecte;
    }

    @Override
    public String toString() {
        return "\nStudent{\n" +
                "numeStudent='" + numeStudent + '\'' +
                ", emailStudent='" + emailStudent + '\'' +
                "\n}";
    }

    @Override
    public int hashCode() {
        return 1;
    }

    /**
     * Override equals
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        String numePrim = student.getNumeStudent();
        String numeDuo = this.getNumeStudent();
        if (numePrim.equals(numeDuo)) {
            return true;
        } else {
            return false;
        }

    }
}
