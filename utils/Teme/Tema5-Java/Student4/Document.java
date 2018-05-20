public abstract class Document 
{
    String name;
    String path;
    public Document(String name,String path)
    {
        this.name = name;
        this.path = path;
    }
    public abstract String toString();
}
