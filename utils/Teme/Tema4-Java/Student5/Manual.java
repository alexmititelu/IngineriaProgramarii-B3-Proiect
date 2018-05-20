import java.nio.file.Path;
import java.nio.file.Paths;

public class Manual extends Document {
    public Manual(String title,String path)
    {
        this.title = title;
        this.path = path;
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
        this.path = path;
    }


    @Override
    public String toString() {
        return "Manual{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
