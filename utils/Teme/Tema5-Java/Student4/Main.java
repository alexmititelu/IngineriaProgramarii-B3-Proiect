
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main 
{
    public static void main(String[] args) throws FileNotFoundException, IOException, MyException
    {
        /*Compulsory
        Catalog catalog = new Catalog();
        catalog.add(new Book("The Art of Computer Programming", "d:\\books\\programming\\tacp.ps", 2016, "Donald E. Knuth"));  
        catalog.add(new Article("Mastering the Game of Go without Human Knowledge", "d:\\articles\\AlphaGo.pdf", 2017, "David Silver", "Julian Schrittwieser", "Karen Simonyan"));
        catalog.add(new Manual("Cookbook", "d:\\stuff\\cookbook.doc"));
        catalog.save("C:\\Users\\Rusu\\Desktop\\PA\\Lab4\\catalog.dat");
        Desktop.getDesktop().open(new File("catalog.dat"));
        //catalog.load("C:\\Users\\Rusu\\Desktop\\PA\\Lab4\\catalog.dat");
        
        catalog.list();*/
       
        //Optional
        Catalog catalog = new Catalog();
        String input,name,path,author1,author2,author3,year;
        String[] params;
        Scanner in = new Scanner(System.in);
        CommandLine cmd = new CommandLine();
        int i=0;
        boolean alive = true;
        while(alive)
        {
            System.out.println("Waiting for input...");
            i=0;
            input = in.nextLine();
            if(input.equals("exit"))
            {
                alive = false;
                System.out.println("Session ended.");
                continue;
            }
            for(char c : input.toCharArray())
                if(c == '"')
                    i++;
            if(i%2 != 0 && i < 4)
            {
                System.err.println("Arguments ar invalid!");
                continue;
            }
            params = input.split(" ");
            switch(params[0].toString())
            {
                case "add":
                    cmd.add(new AddCommand(params));
                    break; 
                case "list":
                    cmd.add(new ListCommand());
                    break;
                case "save":
                    cmd.add(new SaveCommand(params));
                    break;
                case "load":
                    cmd.add(new LoadCommand(params));
                    break;
            }
        }
    }
}
