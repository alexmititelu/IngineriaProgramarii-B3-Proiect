import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Catalog catalog = new Catalog();
            catalog.add(new Book("The Art of Computer Programming", "d:\\books\\programming\\tacp.ps", 1967, "Donald E. Knuth", "Karen Simonyan"));
            catalog.add(new Article("Mastering the Game of Go without Human Knowledge", "d:\\articles\\AlphaGo.pdf", 2017, "David Silver", "Julian Schrittwieser", "Karen Simonyan"));
            catalog.add(new Manual("Cookbook", "d:\\stuff\\cookbook.doc"));

            catalog.save("d:\\[PA]\\Lab4\\src\\catalog.dat");
            catalog.load("d:\\[PA]\\Lab4\\src\\catalog.dat");
            catalog.open("d:\\[PA]\\Lab4\\src\\catalog.dat");
            catalog.list();
        }

        catch(IOException e) {
            e.printStackTrace();
        }
    }

}
