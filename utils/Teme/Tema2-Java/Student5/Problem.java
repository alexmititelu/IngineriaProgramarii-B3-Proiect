import java.util.ArrayList;
import java.util.List;

public class Problem {
    List<Student> studentsList;
    List<Teacher> teachersList;


    public Problem()
    {
        studentsList = new ArrayList<>();
        teachersList = new ArrayList<>();
    }

    public void setStudentsList(List<Student> studentsList) {
        this.studentsList = studentsList;
    }

    public void setTeachersList(List<Teacher> teachersList) {
        this.teachersList = teachersList;
    }

    public void setStudents(Student ... students)
    {
        int i;

        for(i = 0; i < students.length; i++)
            studentsList.add(students[i]);
    }

    public void setTeachers(Teacher ... teachers)
    {
        int i;
        for(i = 0; i < teachers.length; i++)
            teachersList.add(teachers[i]);
    }

    @Override
    public String toString() {
        String studentsInfo = new String();
        String teachersInfo = new String();

        for(Student s : studentsList)
        {
            studentsInfo = studentsInfo + s.toString() + '\n';
        }

        for(Teacher t : teachersList)
        {
            teachersInfo = teachersInfo + t.toString() + '\n';
        }

        return "Problem: \n" + studentsInfo + teachersInfo;
    }

    public Person[] getParticipants()
    {
        int itemCount = studentsList.size() + teachersList.size();
        int k = 0;
        Person[] participants = new Person[itemCount];

        for(Student s : studentsList)
        {
            participants[k++] = s;
        }

        for(Teacher t : teachersList)
        {
            participants[k++] = t;
        }

        return participants;
    }

}
