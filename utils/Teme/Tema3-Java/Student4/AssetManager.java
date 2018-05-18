package assetManager;
import asset.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * AssetManager class cu toate datele sale
 */
public class AssetManager {
    private ArrayList<Item> itemsList;

    /**
     * constructor implicit
     */
    public AssetManager() {
        this.itemsList = new ArrayList<>();
    }

    /**
     * functie getter
     * @return lista de iteme
     */
    public ArrayList<Item> getItems() {
        ArrayList<Item> sortedItems = new ArrayList<>();

        sortedItems.addAll(itemsList);

        sortedItems.sort(Comparator.comparing(Item::getName));

        return sortedItems;
    }

    /**
     * adauga iteme
     * @param item itemul de adaugat
     */
    public void add(Item ... item) {
        this.itemsList.addAll(Arrays.asList(item));
    }

    /**
     * face lista de assets
     * @return o lista de asseturi ordonata
     */
    public ArrayList<Asset> getAssets(){
        ArrayList<Asset> sortedAssets = new ArrayList<>();

        for (Item item:itemsList) {
            if (item instanceof Asset) {
                sortedAssets.add((Asset) item);
            }
        }

        sortedAssets.sort(Comparator.comparing(Asset::computeProfit).reversed());

        return sortedAssets;
    }

    @Override
    public String toString() {
        return "AssetManager {" +
                "\nitemsList: \n" + itemsList +
                '}';
    }
}
