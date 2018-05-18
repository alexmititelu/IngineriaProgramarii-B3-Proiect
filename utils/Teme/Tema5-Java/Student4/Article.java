public class Article extends Document
{
    int year;
    String author;
    String publisher;
    String journalist;
    public Article(String name,String path,int year,String author,String publisher,String journalist) throws MyException
    {
        super(name,path);
        if(year < 1 || year > 2018)
            throw new MyException("Error at inserting article " + name);
        this.year = year;
        this.author = author;
        this.publisher = publisher;
        this.journalist = journalist;
    }
    public String toString()
    {
        return this.name + "," + this.path + "," + this.year + "," + this.author + "," 
                + this.publisher + "," + this.journalist;
    }
}
