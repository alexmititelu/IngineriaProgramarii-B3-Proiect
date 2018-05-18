public class SaveCommand implements Cmd
{
    String path;
    public SaveCommand(String[] params)
    {
        path = params[2].toString();
        path = path.split("\"")[1];
    }
    public String getType()
    {
        return "save";
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
        return Integer.parseInt("23");
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
