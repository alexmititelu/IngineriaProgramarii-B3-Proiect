package ro.uaic.info.ip.proiect.b3.database.objects;

import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class StudentTest {

    @Test
    public void get() {

        Student student = Student.get("validNrMatricol");
        assertThat(student, instanceOf(Student.class));

        assertEquals(null, Student.get("testNrMatricol"));
        assertNotEquals(null, Student.get("validNrMatricol"));
    }

    @Test
    public void getEmail()throws Exception {
        Constructor<Student> constructor= (Constructor<Student>) Student.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        Student student = constructor.newInstance("JtestNrMatricol", "test.Jtest@Jmail.com");

        assertEquals("test.Jtest@Jmail.com", student.getEmail());
        assertNotEquals("", student.getEmail());
    }
}