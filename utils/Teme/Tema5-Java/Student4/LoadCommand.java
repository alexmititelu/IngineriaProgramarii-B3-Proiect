public class LoadCommand implements Cmd{
    String path;
    public LoadCommand(String[] params)
    {
        path = params[2].toString();
        path = path.split("\"")[1];
    }
     public String getType()
    {
        return "load";
    }
    public String getName()
    {
        return "";
    }
    public String getPath()
    {
        return path;
    }
    public int getYear()
    {
        return -1;
    }
    public String getAuthor()
    {
        return "";
    }
    public String getPublisher()
    {
        return "";
    }
    public String getRedactor()
    {
        return "";
    }
}
