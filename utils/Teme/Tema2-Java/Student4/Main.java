package problema;

public class Main {

    /**
     * Declarare lista studenti, profesori etc
     * @param args arhumente cititie de la tastatura
     */
    static public void main(String[] args) {
        Student s1 = new Student("S1", "s1@info.uaic.ro");
        Student s2 = new Student("S2", "s2@info.uaic.ro");
        Student s3 = new Student("S3", "s3@info.uaic.ro");
        Student s4 = new Student("S4", "s4@info.uaic.ro");
        Student s5 = new Student("S5", "s5@info.uaic.ro");

        Teacher t1 = new Teacher("T1", "t1@info.uaic.ro");
        Teacher t2 = new Teacher("T2", "t2@info.uaic.ro");
        Teacher t3 = new Teacher("T3", "t3@info.uaic.ro");

        Project p1 = t1.createProject("P1", 2);
        Project p2 = t1.createProject("P2", 2);
        Project p3 = t1.createProject("P3", 2);

        s1.setPreferences(p1, p2, p3);

        t1.setPreferences(s3, s1, s2, s4);

        Problem problem = new Problem();

        problem.setStudents(s1, s2, s3, s4);
        problem.setTeachers(t1, t2, t3);

//        problem.getParticipants();

        System.out.println(problem.getParticipants());

    }
}
