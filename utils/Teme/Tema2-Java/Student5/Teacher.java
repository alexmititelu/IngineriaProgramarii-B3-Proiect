import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;



public class Teacher extends Person {
    List<Student> teacherPreferences;
    List<Project> offeredProjects;

    public Teacher(String name, String email)
    {
        this.name = name;
        this.email = email;
        teacherPreferences = new ArrayList<>();
        offeredProjects = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTeacherPreferences(List<Student> teacherPreferences) {
        this.teacherPreferences = teacherPreferences;
    }

    public void setOfferedProjects(List<Project> offeredProjects) {
        this.offeredProjects = offeredProjects;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void addPreference(Student s)
    {
        if(!teacherPreferences.contains(s))
            teacherPreferences.add(s);
    }

    public void addProject(Project p)
    {
        if(!offeredProjects.contains(p))
            offeredProjects.add(p);
    }

    public void setPreferences(Student ... students)
    {
        int i;

        for(i = 0; i < students.length; i++)
            if(!teacherPreferences.contains(students[i]))
                teacherPreferences.add(students[i]);
    }

    public void removePreference(Student s)
    {
        for (Iterator<Student> it = teacherPreferences.listIterator(); it.hasNext(); )
        {
            Student nextStudent = it.next();
            if (nextStudent.equals(s))
            {
                it.remove();
            }
        }
    }

    public void removeProject(Project p)
    {
        for (Iterator<Project> it = offeredProjects.listIterator(); it.hasNext(); )
        {
            Project nextProject = it.next();
            if (nextProject.equals(p))
            {
                it.remove();
            }
        }
    }

    public Project createProject(String name, int capacity)
    {
        Project newProject = new Project(name, capacity);

        if(!offeredProjects.contains(newProject))
            this.addProject(newProject);
        return newProject;
    }

    @Override
    public String toString() {
        String teacherPreferencesString = new String();
        String offeredProjectsString = new String();

        for(Student s : teacherPreferences)
        {
            teacherPreferencesString = teacherPreferencesString + s.getName();
            teacherPreferencesString = teacherPreferencesString + ' ';
        }

        for(Project p : offeredProjects)
        {
            offeredProjectsString = offeredProjectsString + p.getName() + ':' + p.getCapacity();
            offeredProjectsString = offeredProjectsString + ' ';
        }

        return "Teacher name: " + this.name + '\n' + "Teacher email: " + this.email + '\n' + "Teacher preferences: " + teacherPreferencesString + "\n" +
                "Teacher offered projects: " + offeredProjectsString;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Teacher other = (Teacher) obj;

        if(!Objects.equals(this.name, other.name)){
            return false;
        }

        if(!Objects.equals(this.email, other.email)){
            return false;
        }

        if (!Objects.equals(this.teacherPreferences, other.teacherPreferences)) {
            return false;
        }
        if (!Objects.equals(this.offeredProjects, other.offeredProjects)) {
            return false;
        }
        return true;
    }



}
