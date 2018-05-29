package ro.uaic.info.ip.proiect.b3.plagiarism;

import it.zielke.moji.MossException;
import it.zielke.moji.SocketClient;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ro.uaic.info.ip.proiect.b3.database.Database;
import ro.uaic.info.ip.proiect.b3.database.objects.cont.Cont;
import ro.uaic.info.ip.proiect.b3.database.objects.hotzone.HotZone;
import ro.uaic.info.ip.proiect.b3.database.objects.plagiat.Plagiat;
import ro.uaic.info.ip.proiect.b3.database.objects.tema.Tema;
import ro.uaic.info.ip.proiect.b3.database.objects.temaexercitiuextensie.TemaExercitiuExtensie;
import ro.uaic.info.ip.proiect.b3.database.objects.temaincarcata.TemaIncarcata;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class PlagiarismDetector {
    private static final Logger logger = Logger.getLogger(PlagiarismDetector.class);

    public static void update() throws SQLException, IOException, MossException {
        logger.info("Se incearca update-ul temelor plagiate ...");

        ArrayList<Tema> temeToBeUpdated = Tema.getAllSubjectsReadyToUpdatePlagiarism();
        logger.info("S-au gasit un numar de " + temeToBeUpdated.size() + " teme pentru care trebuie verificat plagiarismul");

        for (Tema tema : temeToBeUpdated) {
            logger.info("Se incepe verificarea temei " + tema.getNumeTema());

            ArrayList<TemaExercitiuExtensie> exercitii = TemaExercitiuExtensie.getAllExercises(tema.getId());
            logger.info("S-au gasit un numar de " + exercitii.size() + " exercitii pentru aceasta tema");

            for (TemaExercitiuExtensie exercitiu : exercitii) {
                logger.info("Se incepe verificarea exercitiului " + exercitiu.getEnunt());

                ArrayList<TemaIncarcata> temeIncarcate = TemaIncarcata.getAllTemeIncarcate(exercitiu);
                logger.info("S-au gasit un numar de " + temeIncarcate.size() + " teme incarcate pentru acest exercitiu");

                copyFilesInMossFormat(temeIncarcate);
                logger.info("S-au pregatit fisierele pentru uploadul in MOSS");

                if (temeIncarcate.size() != 0) {
                    logger.info("Se incearca uploadarea fisierelul in MOSS");
                    URL mossURL = getMossURL(exercitiu.getExtensieAcceptata());
                    logger.info("S-au uploadat fisierele in MOSS - " + mossURL.toString());

                    logger.info("Se incearca parsarea URL-ului MOSS");
                    parseMossUrl(mossURL, exercitiu.getNrExercitiu(), exercitiu.getIdTema());
                    logger.info("Parsarea s-a terminat cu succes!");
                }

                FileUtils.deleteDirectory(new File("./compare/"));
                logger.info("Stergerea fisierelor ramase din formatul MOSS");
            }

            Database.getInstance().updateOperation("UPDATE teme SET verificata_plagiat = 1 WHERE id = ?", Long.toString(tema.getId()));
        }
    }

    private static void copyFilesInMossFormat(ArrayList<TemaIncarcata> temeIncarcate) throws IOException {
        FileUtils.deleteDirectory(new File("./compare/"));
        new File("./compare").mkdirs();

        for (TemaIncarcata tema : temeIncarcate) {
            String mossDirectory = "./compare/" + tema.getNumeFisierTema();
            new File(mossDirectory).mkdirs();

            Files.copy(
                    Paths.get("./uploaded-user-files/" + tema.getNumeFisierTema()),
                    Paths.get(mossDirectory + "/" + tema.getNumeFisierTema()),
                    StandardCopyOption.REPLACE_EXISTING
            );
        }
    }

    private static String translateExtension(String currentExtension) {
        if (currentExtension.equals("cpp")) return "cc";
        if (currentExtension.equals("c")) return "c";
        if (currentExtension.equals("java")) return "java";
        if (currentExtension.equals("py")) return "python";
        if (currentExtension.equals("sql")) return "plsql";

        return "invalid";
    }

    private static URL getMossURL(String extension) throws MossException, IOException {
        Collection<File> files = FileUtils.listFiles(new File("./compare"), null, true);

        SocketClient socketClient = new SocketClient();
        socketClient.setUserID("520190020");

        extension = translateExtension(extension);

        socketClient.setLanguage(extension);
        socketClient.run();

        for (File f : files) {
            socketClient.uploadFile(f);
        }

        socketClient.sendQuery();
        return socketClient.getResultURL();
    }

    private static void parseMossUrl(URL mossURL, int nrExercitiu, long idTema) throws IOException, SQLException {
        Document document = Jsoup.connect(mossURL.toString()).get();
        Element table = document.select("table").get(0);
        Elements rows = table.select("tr");

        for (int i = 1; i < rows.size(); ++i) {
            Element row = rows.get(i);
            Elements cols = row.select("td");

            String[] tokens = cols.get(0).text().split("/");
            String firstFileName = tokens[tokens.length - 2];
            int procentaj1 =
                    Integer.parseInt(tokens[tokens.length - 1].replace("(", "").replace(")", "").replace("%", "").replace(" ", ""));

            String[] tokens2 = cols.get(1).text().split("/");
            String secondFileName = tokens2[tokens2.length - 2];
            int procentaj2 =
                    Integer.parseInt(tokens2[tokens2.length - 1].replace("(", "").replace(")", "").replace("%", "").replace(" ", ""));

            int procentaj = Math.max(procentaj1, procentaj2);

            if (procentaj > 35) {
                TemaIncarcata temaIncarcata1 = TemaIncarcata.getByNumeFisier(firstFileName);
                Cont cont1 = Cont.getById(temaIncarcata1.getIdCont());

                TemaIncarcata temaIncarcata2 = TemaIncarcata.getByNumeFisier(secondFileName);
                Cont cont2 = Cont.getById(temaIncarcata2.getIdCont());

                long idPlagiat = new Plagiat(cont1.getUsername(), cont2.getUsername(), idTema, nrExercitiu, procentaj).insert();

                Elements link = cols.get(0).select("a");
                String hotZoneUrl = link.attr("href");
                hotZoneUrl = hotZoneUrl.replace(".html", "-top.html");

                populateHotZone(hotZoneUrl, idPlagiat);
            }
        }
    }

    private static void populateHotZone(String hotZoneUrl, long idPlagiat) throws IOException, SQLException {
        Document document = Jsoup.connect(hotZoneUrl).get();
        Element table = document.getElementsByTag("table").get(0);
        Elements rows = table.select("tr");

        for (int i = 1; i < rows.size(); ++i) {
            Element row = rows.get(i);
            Elements cols = row.select("td");

            String[] tokens = cols.get(0).text().split("-");
            String[] tokens2 = cols.get(2).text().split("-");

            new HotZone(idPlagiat,
                    new int[]{Integer.parseInt(tokens[0]) - 1, Integer.parseInt(tokens[1]) - 1},
                    new int[]{Integer.parseInt(tokens2[0]) - 1, Integer.parseInt(tokens2[1]) - 1},
                    100).insert();
        }
    }
}
