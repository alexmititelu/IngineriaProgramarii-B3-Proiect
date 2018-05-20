import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Catalog 
{
    List<Document> list;
    public Catalog()
    {
        list = new LinkedList();
    }
    public void add(Document o)
    {
        list.add(o);
    }
    public void list()
    {
        for(Document i : list)
            System.out.println(i.getClass().getName() + ":" + i.toString());
    }
    public void save(String path) throws FileNotFoundException
    {
        PrintWriter file = new PrintWriter(path);
        for(Document i : list)
            file.append(i.getClass().getName() + ":" + i.toString() + "\n");
        file.close();
    }
    public void load(String path) throws FileNotFoundException, IOException, MyException
    {
        String line;
        String[] split;
        BufferedReader br = new BufferedReader(new FileReader(path));
        while(null != (line = br.readLine()))
        {
            split = line.split(":", 2);
            switch(split[0])
            {
                case "Book":
                    split = split[1].split(",");
                    if(split.length == 4)
                        list.add(new Book(split[0],split[1],Integer.parseInt(split[2]),split[3]));
                    else
                        System.err.println("Error inserting Book: " + Arrays.toString(split));
                    break;
                case "Article":
                    split = split[1].split(",");
                    if(split.length == 6)
                        list.add(new Article(split[0],split[1],Integer.parseInt(split[2]),split[3],split[4],split[5]));
                    else
                        System.err.println("Error inserting Article: " + Arrays.toString(split));
                    break;
                case "Manual":
                    split = split[1].split(",");
                    if(split.length == 2)
                        list.add(new Manual(split[0],split[1]));
                    else
                        System.err.println("Error inserting Manual: " + Arrays.toString(split));
                    break;
            }
        }
        br.close();
    }
}
