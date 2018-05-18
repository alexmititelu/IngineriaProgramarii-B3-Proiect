package info;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Catalog implements Serializable {

    private ArrayList<Item> listItem = new ArrayList<>();

    public void add(Item item) {
        this.listItem.add(item);
    }

    public void list() {
        for (Item item :listItem){
            System.out.println(item.toString());
        }
    }

    public void open(String path)throws  ExceptionCustom {
        File fileOpenPath = new File(path);
        if(fileOpenPath.exists()){
            try {
                Desktop.getDesktop().open(fileOpenPath);
            } catch (Exception exception) {
                System.out.println("Error: " + exception.getMessage());
            }
        }else throw new ExceptionCustom("File doesn't exists.");
    }

    public void save(String cale) {
        try {
            FileOutputStream fileOutput = new FileOutputStream(cale);
            ObjectOutputStream out = new ObjectOutputStream(fileOutput);
            out.writeObject(this);
            out.close();
            fileOutput.close();
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public static Catalog load(String path) throws  ExceptionCustom {
        try {
            File fileInput = new File(path);

            if(!fileInput.exists()) {
                throw new ExceptionCustom("Cannot load file.");
            }

            Catalog catalogLoad = new Catalog();

            FileInputStream fileInput2 = new FileInputStream(path);
            ObjectInputStream input = new ObjectInputStream(fileInput2);

            catalogLoad = (Catalog) input.readObject();

            input.close();
            fileInput2.close();

            return catalogLoad;

        } catch (IOException exception) {

            exception.printStackTrace();

        } catch (ClassNotFoundException exception) {

            System.out.println("Not found the class");
            exception.printStackTrace();

        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }

        return new Catalog();
    }

    
}
