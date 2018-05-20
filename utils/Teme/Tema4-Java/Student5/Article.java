import java.nio.file.Path;
import java.nio.file.*;
import java.util.ArrayList;

public class Article extends Document {

    public class MyException extends Exception{
        public MyException(String Message) {
            super(Message);
        }
    }

    public Article(String title, String path, Integer year,  String ...args)
    {
        try{
        this.title = title;
        try {

                Paths.get(path);

        } catch (InvalidPathException | NullPointerException ex) {
            throw new MyException("Path invalid.");
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
            throw new MyException("Path invalid.");
        }}

        catch(MyException s)
        {
                System.err.println("S-a aruncat exceptia "+s);
        }
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) throws MyException {
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

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", authors='" + authors + '\'' +
                ", year=" + year +
                '}';
    }
}
