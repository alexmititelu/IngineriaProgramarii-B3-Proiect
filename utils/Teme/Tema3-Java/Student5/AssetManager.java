package MainPackage;

import Comparators.AssetComparator;
import Comparators.ItemComparator;
import Interfaces.Algorithm;
import Interfaces.Asset;
import items .*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssetManager {
    private static ItemComparator ITEM_COMPARATOR = new ItemComparator();
    private static AssetComparator ASSET_COMPARATOR = new AssetComparator();

    private List<Item> item_list;
    private List<Asset> asset_list;

    public AssetManager() {
        this.asset_list = new ArrayList<>();
        this.item_list = new ArrayList<>();
    }

    public void add(Building... buildings) {
        item_list.addAll(Arrays.asList(buildings));
        asset_list.addAll(Arrays.asList(buildings));
    }

    public void add(Vehicle... vehicles) {
        item_list.addAll(Arrays.asList(vehicles));
        asset_list.addAll(Arrays.asList(vehicles));
    }

    public void add(Jewel... jewelries) {
        item_list.addAll(Arrays.asList(jewelries));
    }

    public List<Item> getItemList() {
        List<Item> sortedList= new ArrayList<>(item_list);
        sortedList.sort(ITEM_COMPARATOR);
        return sortedList;
    }
    public List<Asset> getAssetList() {
        List<Asset> sortedList= new ArrayList<>(asset_list);
        sortedList.sort(ASSET_COMPARATOR);
        return sortedList;
    }
    
    public void generateProblem(int nrItems, int maxSurface, int maxPerformance, int maxPrice)
    {
        int i, objCreated, nrBuildings = 1, nrVehicles = 1, range;
        for(i = 0; i < nrItems; i++)
        {
            objCreated = (int)Math.round(Math.random());
            if(objCreated == 0)
            {
                this.add(new Building("House " + nrBuildings, (int)(Math.random() * maxPrice) + 1, (int)(Math.random() * maxSurface) + 1)); 
                nrBuildings++;
            }
            else
            {
                this.add(new Vehicle("Car " + nrVehicles, (int)(Math.random() * maxPrice) + 1, (int)(Math.random() * maxPerformance) + 1));
                nrVehicles++;
            }
        }
    }
    
    public Portofolio createPortofolio(Algorithm algorithm, int maxValue) {
        return algorithm.solveAlgorithm(this.getAssetList(),maxValue);
    }
}

