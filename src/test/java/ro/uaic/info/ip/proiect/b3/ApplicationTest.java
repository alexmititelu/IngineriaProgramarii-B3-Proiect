package ro.uaic.info.ip.proiect.b3;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import ro.uaic.info.ip.proiect.b3.model.TestForClass;

public class ApplicationTest {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestForClass.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}