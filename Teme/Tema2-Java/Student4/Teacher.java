package problema;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clasa Teacher
 * Memoreaza toate datele despre profesori
 */
public class Teacher extends Person {
    private String numeProfesor;
    private String emailProfesor;
    private ArrayList<Student> listaPreferinte = new ArrayList<>();

    /**
     * contructor Teacher
     * @param nume numele profesorului
     * @param email email-ul profesorului
     */
    public Teacher(String nume, String email) {
        this.numeProfesor = nume;
        this.emailProfesor = email;
    }

    /**
     * Creare proiect
     * @param numeProj numele proiectului
     * @param dimensiuneProj dimensiunea maxima pe care o poate lua proiectul
     * @return
     */
    public Project createProject(String numeProj, int dimensiuneProj) {
        return new Project(numeProj, dimensiuneProj);
    }

    /**
     * Seteaza preferintele profesorului
     * @param stud studentii alesi
     */
    public void setPreferences(Student ... stud) {
        this.listaPreferinte.addAll(Arrays.asList(stud));
    }

    public String getNumeProfesor() {
        return numeProfesor;
    }

    public String getEmailProfesor() {
        return emailProfesor;
    }

    public ArrayList<Student> getListaPreferinte() {
        return listaPreferinte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher prof = (Teacher) o;
        String numePrim = prof.getNumeProfesor();
        String numeDuo = this.getNumeProfesor();
        if (numePrim.equals(numeDuo)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {

        return 1;
    }

    @Override
    public String toString() {
        return "\nTeacher{\n" +
                "numeProfesor='" + numeProfesor + '\'' +
                ", emailProfesor='" + emailProfesor + '\'' +
                "\n}";
    }
}
