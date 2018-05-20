package asset;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Portfolio class cu toate datele sale
 */
public class Portfolio {
    private ArrayList<Item> assetsList;
    private int maxValue;

    /**
     * Constructor pentru clasa Portfolio
     * @param assetsList
     * @param maxValue
     */
    public Portfolio(ArrayList<Item> assetsList, int maxValue) {
        assetsList.sort(Comparator.comparing(Item::getPrice).reversed());
        this.assetsList = assetsList;
        this.maxValue = maxValue;
    }

    /**
     * algoritm de rezolvare a solutiei
     * @return o posibila solutie
     */
    public ArrayList<Item> solveProblem() {
        ArrayList<Item> solution = new ArrayList<>();

        for (Item item:assetsList) {
            if (item instanceof Asset) {
                if ((this.maxValue - item.getPrice()) >= 0) {
                    solution.add(item);
                    this.maxValue -= item.getPrice();
                }
            }
        }

        return solution;
    }
}
