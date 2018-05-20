package compulsory;

import java.util.ArrayList;
import java.util.List;

public class Portofolio {
    private List<IAsset> portofolioItems;

    public Portofolio() {
        portofolioItems = new ArrayList<>();
    }

    public void addItem( IAsset asset){
        portofolioItems.add(asset);
    }

    @Override
    public String toString() {
        return portofolioItems.toString();
    }
}
