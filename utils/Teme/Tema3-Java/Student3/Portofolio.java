package lab3;

/**
 *
 * @author Aurelia
 */
import lab3.asset.Asset;
import lab3.item.Item;
import java.util.LinkedList;
import java.util.List;

public class Portofolio {
    private List <Asset> assets;

        assets = new LinkedList <>();
    /**
     * Constructor without arguments
     */
    public Portofolio() {
    }

    /**
     * Constructor with arguments
     * @param assets the list of assets to be assigned
     */
    public Portofolio(List <Asset> assets) {
        this.assets = new LinkedList <>(assets);
    }

    /**
     * Adds an asset to a list of assets
     * @param asset
     */
    public void addAsset(Asset asset) {
        assets.add(asset);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Selected assets: ");

        for (Asset asset : assets) {
            Item item = (Item) asset;
            stringBuilder.append(item.getName());
            stringBuilder.append(", ");
        }

        return stringBuilder.toString();
    }

    private static class assets {

        public assets() {
        }
    }
}

