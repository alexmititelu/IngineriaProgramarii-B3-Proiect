import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Book extends Document{


    public Book(String title, String path, Integer year,  String ...args)
    {
        try{
        this.title = title;
            try {

                Paths.get(path);

            } catch (InvalidPathException | NullPointerException ex) {
                throw new Book.MyException("Path invalid.");
            }
        this.path = path;
        this.authors = new ArrayList();
        for(int i=0;i<args.length;i++)
            authors.add(args[i]);
        if(!(year>=1000&&year<=5000))
            throw new MyException("An invalid.");
        this.year = year;}
        catch(MyException s)
        {
            System.err.println("S-a aruncat exceptia "+s);
        }

    }
    public class MyException extends Exception{
        public MyException(String Message) {
            super(Message);
        }
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        try{try {

            Paths.get(path);
            this.path = path;

        } catch (InvalidPathException | NullPointerException ex) {
            throw new Book.MyException("Path invalid.");
        }}

        catch(Book.MyException s)
        {
            System.err.println("S-a aruncat exceptia "+s);
        }
    }

    public ArrayList<String> getAuthor() {
        return authors;
    }

    public void setAuthor(ArrayList<String> author) {
        this.authors = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) throws MyException{
        try {
            if(!(year>=1000&&year<=5000))
                throw new MyException("An invalid.");
            this.year = year;
        }
        catch(MyException s)
        {
            System.err.println("S-a aruncat exceptia "+s);
        }
    }
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", authors='" + authors + '\'' +
                ", year=" + year +
                '}';
    }
}
