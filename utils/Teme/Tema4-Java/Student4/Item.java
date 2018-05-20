package info;

import java.io.File;
import java.io.Serializable;

/**
 * Clasa Item cu toate datele
 */
public abstract class Item implements Serializable {

    private String name = "";
    private String path = "";

    /**
     * Constructor implicit Item
     * @param name numele unui item
     * @param path calea item-ului
     */
    public Item(String name, String path) {

        try {

            this.name = name;
            this.setPath(path);

        } catch(Exception exception){

            System.out.println("Error: " + exception.getMessage());

        }
    }

    /**
     * Functia setPath verifica si seteaza path-ul daca este corect
     * @param path calea item-ului
     * @throws ExceptionCustom arunca o exceptie daca path-ul nu este corect
     */
    public void setPath (String path) throws ExceptionCustom {
        File filePath = new File(path);

        if(filePath.exists()) {

            this.path = path;

        } else {

            throw new ExceptionCustom("File doesn't exists.");

        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
