package ro.uaic.info.ip.proiect.b3.generators;

import ro.uaic.info.ip.proiect.b3.database.Database;

import java.sql.*;
import java.util.Random;

public class TokenGenerator {
    private static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String numbers = "0123456789";

    private static String getTokenDomain() {
        return letters + numbers + letters.toLowerCase();
    }

    private static String generateToken(int size) {
        String domain = getTokenDomain();
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int randomIndex = Math.abs(random.nextInt()) % domain.length();
            token.append(domain.charAt(randomIndex));
        }
        return token.toString();
    }

    private static boolean isTokenAlreadyUsed(String token, String tableName) {
        boolean isTokenUsed = false;
        Connection dbConnection = null;

        try {
            dbConnection = Database.getInstance().getConnection();
            String query = "SELECT * FROM " + tableName + " WHERE token LIKE ?";

            Statement queryStatement = dbConnection.prepareStatement(query);
            ((PreparedStatement) queryStatement).setString(1, token);

            ResultSet resultSet = ((PreparedStatement) queryStatement).executeQuery();

            while (resultSet.next()) {
                isTokenUsed = true;
                break;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                dbConnection.close();
            } catch (Exception e) {
                System.out.println("[" + System.nanoTime() + "] " + e.getMessage());
            }
        }

        return isTokenUsed;
    }

    public static String getToken(int size, String tableName) {
        String generatedToken;

        do {
            generatedToken = generateToken(size);
        } while (isTokenAlreadyUsed(generatedToken, tableName));

        return generatedToken;
    }

}