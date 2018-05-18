import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;



public class Student extends Person {
    List<Project> studentPreferences;

    public Student(String name, String email)
    {
        this.name = name;
        this.email = email;
        studentPreferences = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStudentPreferences(List<Project> studentPreferences) {
        this.studentPreferences = studentPreferences;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void addPreference(Project p)
    {
        if(!studentPreferences.contains(p))
            studentPreferences.add(p);
    }

    public void setPreferences(Project ... projects)
    {
        int i;
        for(i = 0; i < projects.length; i++)
            if(!studentPreferences.contains(projects[i]))
                studentPreferences.add(projects[i]);
    }

    public void removePreference(Project p)
    {
        for (Iterator<Project> it = studentPreferences.listIterator(); it.hasNext(); )
        {
            Project nextProject = it.next();
            if (nextProject.equals(p))
            {
                it.remove();
            }
        }
    }

    @Override
    public String toString() {
        String preferences = new String();
        for(Project p : studentPreferences)
        {
            preferences = preferences + p.getName();
            preferences = preferences + ' ';
        }
        return "Student name: " + this.name + '\n' + "Student email: " + this.email + '\n' + "Student preferences: " + preferences + '\n';
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
        final Student other = (Student) obj;

        if(!Objects.equals(this.name, other.name)){
            return false;
        }

        if(!Objects.equals(this.email, other.email)){
            return false;
        }

        if (!Objects.equals(this.studentPreferences, other.studentPreferences)) {
            return false;
        }
        return true;
    }


}
