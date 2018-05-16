package ro.uaic.info.ip.proiect.b3.permissions;

import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.contconectat.ContConectat;
import ro.uaic.info.ip.proiect.b3.database.objects.didactic.Didactic;
import ro.uaic.info.ip.proiect.b3.database.objects.didactic.exceptions.DidacticException;
import ro.uaic.info.ip.proiect.b3.database.objects.materie.Materie;
import ro.uaic.info.ip.proiect.b3.database.objects.profesor.Profesor;
import ro.uaic.info.ip.proiect.b3.database.objects.registerlink.RegisterLink;

import java.sql.SQLException;

/**
 * Aceasta clasa reprezinta un ajutor pentru a verifica identitatea utilizatorilor.
 */
public class PermissionManager {
    /**
     * @param loginToken tokenul de login al unui utilizator
     * @return true in cazul in care tokenul de login al utilizatorului se afla in baza de date si fals in caz contrar
     */
    public static boolean isUserLoggedIn(String loginToken) throws SQLException {
        ContConectat contConectat = ContConectat.getByToken(loginToken);
        return (contConectat != null);
    }

    /**
     * @param loginToken tokenul de login al unui utilizator
     * @return username-ul ce corespunde tokenului de login sau null in cazul in care tokenul nu este gasit in baza de date
     */
    public static String getUsernameLoggedIn(String loginToken) throws SQLException {
        ContConectat contConectat = ContConectat.getByToken(loginToken);
        return (contConectat != null) ? contConectat.getUsername() : null;
    }


    public static boolean isLoggedUserProfesor(String loginToken) throws SQLException {
        Cont cont = Cont.getByLoginToken(loginToken);
        return (cont != null && cont.getPermission() > 1);
    }

    /**
     * @param username username-ul unui utilzator
     * @param password parola hashuita al utilizatorului -- final String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
     * @return true in cazul in care datele sunt valide si fals in caz contrar
     */


    public static boolean isLoginDataValid(String username, String password) throws SQLException {
        Cont cont = Cont.getByUsername(username);
        return (cont != null && cont.getPassword().equals(password));
    }

    /**
     * @param registerToken un token de inregistrare
     * @return true in cazul in care tokenul se afla in baza de date iar fals in caz contrar
     */
    public static boolean isRegisterTokenValid(String registerToken) throws SQLException {
        RegisterLink registerLink = RegisterLink.getByToken(registerToken);
        return (registerLink != null);
    }

    /**
     * @param registerToken un token de inregistrare
     * @return emailul asociat tokenului de inregistrare dat ca parametru sau null in cazul in care tokenul nu este gasit in baza de date
     */
    public static String getEmailForRegisterToken(String registerToken) throws SQLException {
        RegisterLink registerLink = RegisterLink.getByToken(registerToken);
        return (registerLink != null) ? registerLink.getEmail() : null;
    }

    public static boolean isUserAllowedToCreateHomeworkOnSubject(String numeMaterie, String loginToken) throws SQLException {
        try {
            Cont cont = Cont.getByLoginToken(loginToken);
            Materie materie = Materie.getByTitlu(numeMaterie);

            if (cont != null && materie != null) {
                Profesor profesor = Profesor.getByEmail(cont.getEmail());

                if (profesor != null) {
                    Didactic didactic = Didactic.getByIdMaterieAndIdProfesor(materie.getId(), profesor.getId());
                    return (didactic != null);
                }
            }

            return false;
        } catch (DidacticException e) {
            return false;
        }
    }
}
