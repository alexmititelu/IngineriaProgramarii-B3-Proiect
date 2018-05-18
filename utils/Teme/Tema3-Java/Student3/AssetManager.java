package lab3;

import lab3.algorithm.Algorithm;
import lab3.algorithm.Portofolio;
import lab3.asset.Asset;
import lab3.comparators.AssetComparator;
import lab3.comparators.ItemComparator;
import lab3.item.Item;
import java.util.*;
public class AssetManager {
    private static final Comparator <Item> ITEM_COMPARATOR = new ItemComparator();
    private static final Comparator <Asset> ASSET_COMPARATOR = new AssetComparator();
    private Set <Item> items;

    /**
     * Constructor without arguments
     */
    public AssetManager() {
        items = new HashSet <>();
    }

    /**
     * Adds a variable number of objects of type Item to a set of items
     * @param items the items to be added
     */
    public void add(Item... items) {
        this.items.addAll(Arrays.asList(items));
    }

    /**
     * Gets a list of items sorted by their name
     * @return a list of items sorted by their name
     */
    public List <Item> getItems() {
        List <Item> copy = new LinkedList <>(items);
        copy.sort(ITEM_COMPARATOR);

        return copy;
    }

    /**
     * Gets a list of assets sorted by their profit
     * @return a list of assets sorted by their profit
     */
    public List <Asset> getAssets() {
        List <Asset> assets = new LinkedList <>();
        for (Item item : items) {
            if (item instanceof Asset) {
                assets.add((Asset) item);
            }
        }
        assets.sort(ASSET_COMPARATOR);

        return assets;
    }

    /**
     * Creates a portfofolio of assets such that:
       -- the total price does not exceed a given maximum value.
       -- the profit is as large as possible.
     * @param algorithm the algorithm to be applied
     * @param maxValue given maximum value
     * @return a portofolio (list of selected assets)
     */
    public Portofolio createPortofolio(Algorithm algorithm, int maxValue) {
        List <Asset> assets = getAssets();
        algorithm.orderAssetsAccordingToStrategy(assets);

        Portofolio portofolio = new Portofolio();
        for (Asset asset : assets) {
            Item item = (Item) asset;
            if (item.getPrice() <= maxValue) {
                portofolio.addAsset(asset);
                maxValue = maxValue - item.getPrice();
            }
        }

        return portofolio;
    }

}