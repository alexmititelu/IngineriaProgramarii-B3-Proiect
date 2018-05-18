package problema;

/**
 * Clasa Proiect
 * Instantiaza un proiect cu toate datele despre acesta
 */
public class Project {
    private String numeProiect;
    private int capacitateProiect;

    /**
     * Constructor Proiect
     * @param nume numele proiectului
     * @param capacitate capacitatea maxima a proiectului
     */
    public Project(String nume, int capacitate) {
        this.numeProiect = nume;
        this.capacitateProiect = capacitate;
    }

    public String getNumeProiect() {
        return numeProiect;
    }

    public int getCapacitateProiect() {
        return capacitateProiect;
    }

    @Override
    public String toString() {
        return "Project{" +
                "numeProiect='" + numeProiect + '\'' +
                ", capacitateProiect=" + capacitateProiect +
                '}';
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
