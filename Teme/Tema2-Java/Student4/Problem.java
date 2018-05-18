package problema;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clasa Problem
 * Instantiaza o problema si toate informatiile despre aceasta
 */
public class Problem {
    private ArrayList<Student> listaStudenti = new ArrayList<>();
    private ArrayList<Teacher> listaProfesori = new ArrayList<>();

    /**
     * Returneaza toti participantii la problema
     * @return lista de participanti: Studenti, Profesori
     */
    public ArrayList<Person> getParticipants() {
        ArrayList<Person> listaTotala = new ArrayList<>();

        listaTotala.addAll(listaStudenti);
        listaTotala.addAll(listaProfesori);


        return listaTotala;
    }

    public void setStudents(Student ... stud) {
        for (int i = 0; i < this.listaStudenti.size(); i++) {
            for (int j = 0; j < this.listaStudenti.size(); j++) {
                if (i != j) {
                    if (listaStudenti.get(i).equals(listaStudenti.get(j))) {
                        System.out.println("eroare adaugare student");
                        return;
                    }
                }
            }
        }

        this.listaStudenti.addAll(Arrays.asList(stud));
    }

    public void setTeachers(Teacher ... prof) {
        for (int i = 0; i < this.listaProfesori.size(); i++) {
            for (int j = 0; j < this.listaProfesori.size(); j++) {
                if (i != j) {
                    if (listaProfesori.get(i).equals(listaProfesori.get(j))) {
                        System.out.println("eroare adaugare profesor");
                        return;
                    }
                }
            }
        }

        this.listaProfesori.addAll(Arrays.asList(prof));
    }

    public ArrayList<Student> getListaStudenti() {
        return listaStudenti;
    }

    public ArrayList<Teacher> getListaProfesori() {
        return listaProfesori;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "listaStudenti=" + listaStudenti +
                ", listaProfesori=" + listaProfesori +
                '}';
    }

    @Override
    public int hashCode() {

        return 1;
    }
}
