
import java.io.FileNotFoundException;

public class CommandLine 
{
    Catalog catalog;
    public CommandLine()
    {
        catalog = new Catalog();
    }
    public void add(Cmd o) throws FileNotFoundException, MyException
    {
        switch(o.getType().toString())
        {
            case "book":
                catalog.add(new Book(o.getName(),o.getPath(),o.getYear(),o.getAuthor()));
                break;
            case "article":
                catalog.add(new Article(o.getName(),o.getPath(),o.getYear(),o.getAuthor(),o.getPublisher(),o.getRedactor()));
                break;
            case "manual":
                catalog.add(new Manual(o.getName(),o.getPath()));
                break;
            case "list":
                catalog.list();
                break;
            case "save":
                catalog.save(o.getPath());
                break;
        }
    }
}
