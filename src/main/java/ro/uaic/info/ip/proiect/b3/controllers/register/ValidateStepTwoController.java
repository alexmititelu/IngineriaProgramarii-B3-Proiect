package ro.uaic.info.ip.proiect.b3.controllers.register;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.uaic.info.ip.proiect.b3.database.Database;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.sql.*;

/**
 * Aceasta clasa reprezinta un controller pentru metoda POST a paginii de inregistrare din al doilea pas. (Vezi utilis/autentificare/ pentru detalii despre sistemul de autentificare)
 */
@Controller
public class ValidateStepTwoController {
    /**
     * Metoda returneaza body-ul raspunsului HTTP pentru o cerere de inregistrare pentru al doilea pas.
     * <p>
     * 1. Se valideaza tokenul de inregistrare. (Existenta sa in tabela register_links)
     * 2. Se valideaza emailul primit. (Existenta sa in tabela studenti si inexsistenta in tabela conturi)
     * 3. Se valideaza username-ul. (Acesta trebuie sa contina doar caractere alfanumerice si caracterul '.' (punct) maxim o singura data si sa aiba o lungime de
     * cel putin 6 caractere si cel mult 30)
     * 4. Se valideaza parola. (Aceasta trebuie sa contina doar caractere alfanumerice si sa aiba o lungime de minim 8)
     * 5. Se hashuieste parola folosing sha256. -- final String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString()
     * 6. Se insereaza datele in tabela conturi. (username-ul, emailul si parola hashuita)
     * 7. In cazul in care inserarea s-a realizat cu succes se va sterget tokenul de inregistrare din baza de date.
     * 8. Se returneaza un mesaj pozitiv
     * In cazul in care una dintre validarile de la pasii 1-4 esueaza:
     * 1. Setarea raspunsului drept 400 Bad Request cu ajutorul parametrului response.  -- response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
     * 2. Trimiterea unui raspuns negativ.
     *
     * @param email         emailul trimis de catre utilizator
     * @param username      usernameul trimis de catre utilizator
     * @param password      parola trimisa de catre utilizator
     * @param registerToken tokenul de inregistrare
     * @param response      un obiect java reprezentand raspunsul http; cu ajutorul acestuia se pot seta headere, se poate seta codul de status etc.
     * @return mesaj in functie de rezultatul validarii
     */


    @RequestMapping(value = "/register/{registerToken}", method = RequestMethod.POST)
    public @ResponseBody
    String register(
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @PathVariable String registerToken,
            HttpServletResponse response) {

        if (!isTokenValid(registerToken)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Invalid token!";
        }

        if (!emailValidation(email)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Email deja folosit";
        }

        if (!isUsernameValid(username)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Numele de utilizator este invalid!";
        }

        if (!passwordValidation(password)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Parola este invalida";
        }

        final String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();

        addUser(username, email, hashedPassword);
        removeToken(registerToken);

        return "Inregistrare cu succes";
    }

    private boolean isTokenValid(String registerToken) {
        boolean isTokenInDatabase = false;
        Connection dbConnection = null;

        try {
            dbConnection = Database.getInstance().getConnection();

            String query = "SELECT token FROM register_links WHERE token = ?";
            PreparedStatement queryStatement = dbConnection.prepareStatement(query);
            queryStatement.setString(1, registerToken);

            ResultSet resultSet = ((PreparedStatement) queryStatement).executeQuery();

            if (resultSet.next()) {
                isTokenInDatabase = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                dbConnection.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return isTokenInDatabase;
    }

    private boolean emailValidation(String email) {
        boolean response = true;
        Connection dbConnection = null;

        try {
            dbConnection = Database.getInstance().getConnection();

            String query = "SELECT email FROM conturi WHERE email = ?";
            String query2 = "SELECT email FROM studenti WHERE email = ?";

            Statement queryStatement = dbConnection.prepareStatement(query);
            Statement queryStatement2 = dbConnection.prepareStatement(query2);
            ((PreparedStatement) queryStatement).setString(1, email);
            ((PreparedStatement) queryStatement2).setString(1, email);

            ResultSet resultSet = ((PreparedStatement) queryStatement).executeQuery();
            ResultSet resultSet2 = ((PreparedStatement) queryStatement2).executeQuery();

            if ((!resultSet2.next()) || (resultSet.next())) {
                response = false;
            }
        } catch (Exception e) {
            System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
        } finally {
            try {
                dbConnection.close();
            } catch (Exception e) {
                System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
            }
        }

        return response;
    }

    private boolean isUsernameValid(String username) {
        boolean isUsernameValid = true;
        Connection dbConnection = null;

        try {
            dbConnection = Database.getInstance().getConnection();
            String query = "SELECT username FROM conturi WHERE username = ?";
            Statement queryStatement = dbConnection.prepareStatement(query);
            ((PreparedStatement) queryStatement).setString(1, username);

            ResultSet resultSet = ((PreparedStatement) queryStatement).executeQuery();

            if (resultSet.next()) {
                isUsernameValid = false;
            }

            if (!checkUsername(username)) {
                isUsernameValid = false;
            }
        } catch (Exception e) {
            System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
        } finally {
            try {
                dbConnection.close();
            } catch (Exception e) {
                System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
            }
        }

        return isUsernameValid;
    }

    private boolean passwordValidation(String password) {
        if (password.length() < 8)
            return false;

        if (!password.matches("([A-Z]|[a-z]|[0-9])+")) {
            return false;
        }
        return true;
    }

    private boolean checkUsername(String username) {
        if (username.length() < 6 || username.length() > 30)
            return false;

        if (!username.matches("([A-Z]|[a-z]|[0-9])+")) {
            return false;
        }

        return true;
    }

    private void addUser(String username, String email, String hashedPassword) {
        Connection dbConnection = null;
        try {
            dbConnection = Database.getInstance().getConnection();

            String query = "INSERT INTO conturi (username, email, password) VALUES (?,?,?)";
            Statement queryStatement = dbConnection.prepareStatement(query);
            ((java.sql.PreparedStatement) queryStatement).setString(1, username);
            ((java.sql.PreparedStatement) queryStatement).setString(2, email);
            ((java.sql.PreparedStatement) queryStatement).setString(3, hashedPassword);

            ((java.sql.PreparedStatement) queryStatement).executeUpdate();
        } catch (SQLException e) {
            System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
        } finally {
            try {
                dbConnection.close();
            } catch (Exception e) {
                System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
            }
        }
    }

    private void removeToken(String registerToken) {
        Connection dbConnection = null;
        try {
            dbConnection = Database.getInstance().getConnection();
            String query = "delete from register_links where token = ?";
            Statement queryStatement = dbConnection.prepareStatement(query);
            ((java.sql.PreparedStatement) queryStatement).setString(1, registerToken);

            ((java.sql.PreparedStatement) queryStatement).executeUpdate();
        } catch (Exception e) {
            System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
        } finally {
            try {
                dbConnection.close();
            } catch (Exception e) {
                System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
            }
        }
    }
}