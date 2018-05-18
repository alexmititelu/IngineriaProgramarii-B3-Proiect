public class Book extends Document
{
    int year;
    String author;
    public Book(String name, String path, int year,String author) throws MyException
    {
        super(name,path);
        if(year < 1 || year > 2018)
            throw new MyException("Error at inserting book " + name);
        this.year = year;
        this.author = author;
    }
    public String toString()
    {
        return this.name + "," + this.path + "," + this.year + "," + this.author;
    }
}
