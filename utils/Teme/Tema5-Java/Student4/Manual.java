public class Manual extends Document
{
    public Manual(String name, String path)
    {
        super(name,path);
    }
    public String toString()
    {
        return this.name + "," + this.path;
    }
}
