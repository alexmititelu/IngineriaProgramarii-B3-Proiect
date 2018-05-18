public class Main {
    public static void main(String[] args) {
        Student s1 = new Student("S1", "s1@info.uaic.ro");
        Teacher t1 = new Teacher("T1", "t1@info.uaic.ro");
        Teacher t2 = new Teacher("T2", "t2@info.uaic.ro");

        Project p1 = t1.createProject("P1", 2); //capacity of the project is 2
        t1.addProject(p1);
        s1.setPreferences(p1, p1);
        t1.setPreferences(s1);

        Problem problem = new Problem();
        problem.setStudents(s1);
        problem.setTeachers(t1);
        System.out.println(t1.equals(p1));

        Person[] array = problem.getParticipants();
        System.out.println(array[0]);
    }

}

