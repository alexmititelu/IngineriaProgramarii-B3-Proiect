package ro.uaic.info.ip.proiect.b3.generators;

import ro.uaic.info.ip.proiect.b3.database.objects.temaincarcata.TemaIncarcata;
import java.sql.SQLException;
import java.util.Random;

public class FileNameGenerator {
    private static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String numbers = "0123456789";

    private static String getFileNameDomain() {
        return letters + numbers + letters.toLowerCase();
    }

    private static String generateFileName(int size) {
        String domain = getFileNameDomain();
        StringBuilder fileName = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            int randomIndex = Math.abs(random.nextInt()) % domain.length();
            fileName.append(domain.charAt(randomIndex));
        }

        return fileName.toString();
    }

    private static boolean isFileNameAlreadyUsed(String fileName) throws SQLException {

        if(TemaIncarcata.getByNumeFisier(fileName) == null) {
            return true;
        }

        return false;
    }

    public static String getNewFileName() throws SQLException {
        String generatedFileName;

        do {
            generatedFileName = generateFileName(64);
        } while (!isFileNameAlreadyUsed(generatedFileName));

        return generatedFileName;
    }
}
