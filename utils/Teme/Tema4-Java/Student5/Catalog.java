import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.awt.Desktop;
public class Catalog {
    ArrayList<Document> documents;

    public Catalog() {
        documents=new ArrayList<>();
    }
    public void add(Document document)
    {
        documents.add(document);
    }

    public void load(String path) throws IOException {
        try{
            ObjectInputStream in =new ObjectInputStream(new FileInputStream(path));
            documents=(ArrayList<Document>) in.readObject();
        }
        catch(FileNotFoundException e)
        { System.out.println("Fisierul nou..."); }
        catch(Exception e)
        { System.out.println("Eroare la citirea datelor...");
        }

        System.out.println("Lista documente:"+documents);
    }

    public void save(String paths) throws IOException
    {
        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(paths));
        out.writeObject(documents);
        out.close();

    }
    public void list()
    {
        documents.toString();
    }
    public void open(String path) throws IOException
    {
        //text file, should be opening in default text editor
        File file = new File(path);

        //first check if Desktop is supported by Platform or not
        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        if(file.exists()) desktop.open(file);

    }
}
