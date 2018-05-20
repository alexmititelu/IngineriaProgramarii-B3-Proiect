import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;

abstract class Document implements Serializable {
    String title;
    String path;
    ArrayList<String> authors;
    Integer year;

    abstract String getTitle();

    abstract void setTitle(String title) ;

    abstract String getPath() ;

    abstract void setPath(String path) ;


}
