package asset;

import assetManager.AssetManager;

/**
 * Main project
 */
public class Main {
    static public void main(String[] args) {
        /**
         * instantiere iteme
         */
        Building h1 = new Building("House 1", 27, 9);
        Vehicle c1 = new Vehicle("Car 1", 8, 4);
        Jewel j1 = new Jewel("Diamant", 12);

        AssetManager manager = new AssetManager();

        manager.add(h1, c1, j1);

        Portfolio p1 = new Portfolio(manager.getItems(), 10);

        System.out.println(p1.solveProblem());
    }
}
