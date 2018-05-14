package ro.uaic.info.ip.proiect.b3.authentication;

import ro.uaic.info.ip.proiect.b3.database.objects.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.Student;

public class RegistrationValidator {
    public boolean isEmailValid(String email) {
        Student student = Student.getByEmail(email);
        Cont cont = Cont.getByEmail(email);

        return ((student != null) && (cont == null));
    }

    public boolean isUsernameValid(String username) {
        Cont cont = Cont.getByUsername(username);

        return ((cont == null) && (isUsernameRespectingConstraints(username)));
    }

    private boolean isUsernameRespectingConstraints(String username) {
        if (username.length() < 6 || username.length() > 30)
            return false;

        return username.matches("([A-Z]|[a-z]|[0-9])+");
    }

    public boolean isPasswordRespectingConstraints(String password) {
        return (password.length() > 8 && password.matches("([A-Z]|[a-z]|[0-9])+"));
    }
}
