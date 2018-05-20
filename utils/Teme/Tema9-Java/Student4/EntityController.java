
import java.util.List;

public abstract class EntityController {
    public abstract void insert(List params);                         //C
    public abstract List select(List<String> items,String condition); //R
    public abstract void update(List params,String condition);        //U
    public abstract void delete(String condition);                    //D
}
