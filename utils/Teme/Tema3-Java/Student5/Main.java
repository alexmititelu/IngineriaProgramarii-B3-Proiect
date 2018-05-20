package MainPackage;

import Algorithms.GreedyAlgorithm;
import Algorithms.PDAlgorithm;
import Algorithms.RandomAlgorithm;
import MainPackage.AssetManager;
import items.Building;
import items.Jewel;
import items.Vehicle;

public class Main {
    public static void main(String[] args) {
        /*Building h1 = new Building("House 1", 9, 27);
        Building h2 = new Building("House 2", 9, 27);
        Building h3 = new Building("House 3", 16, 64);

        Vehicle c1 = new Vehicle("Car 1", 4, 8);
        Vehicle c2 = new Vehicle("Car 2", 4, 8);

        Jewel ring = new Jewel("Gold Diamond Ring", 2);

        AssetManager manager = new AssetManager();
        //manager.generateProblem(5, 26, 15, 10);
        manager.add(h1, h2, h3);
        manager.add(c1, c2);
        manager.add(ring);

        System.out.println("Items sorted by name: " + manager.getItemList());
        System.out.println("Assets sorted by profit: " + manager.getAssetList());

        Portofolio result = manager.createPortofolio(new RandomAlgorithm(),25);
        System.out.println("Assets selected by random algorithm: " + result.toString());

        Portofolio resultGreedy = manager.createPortofolio(new GreedyAlgorithm(),25);
        System.out.println("Assets selected by greedy algorithm: " + resultGreedy.toString());
        
        Portofolio resultPD = manager.createPortofolio(new PDAlgorithm(), 25);
        System.out.println("Assets selected by PD algorithm: " + resultPD.toString());*/
        
        Building h1=new Building("House 1",9,27);
        Building h2=new Building("House 2",9,27);
        Building h3=new Building("House 3",16,64);
        Vehicle c1=new Vehicle("Car 1",4,8);
        Vehicle c2=new Vehicle("Car 2",4,8);
        Jewel ring=new Jewel("Gold Diamond Ring",2);
        AssetManager manager=new AssetManager();
        manager.add(h1,h2,h3);
        manager.add(c1,c2);
        manager.add(ring);
        System.out.println("Items sorted by name: "+manager.getItemList());
        System.out.println("\nAssets sorted by profit: "+manager.getAssetList());
        int maxValue = 20;
        Portofolio randomSolution = manager.createPortofolio(new RandomAlgorithm(), maxValue);
        System.out.println("\nThe best portofolio - Random Solution: " + randomSolution);
        Portofolio greedySolution = manager.createPortofolio(new GreedyAlgorithm(), maxValue);
        System.out.println("\nThe best portofolio - Greedy Solution: " + greedySolution);
        Portofolio PDSolution=manager.createPortofolio(new PDAlgorithm(),maxValue);
        System.out.println("\nThe best portofolio - PD Solution: " + PDSolution);

    }
}
