package Comparators;

import Interfaces.Asset;

import java.util.Comparator;

public class AssetComparator implements Comparator<Asset> {

    @Override
    public int compare(Asset asset1, Asset asset2) {
        return Double.compare(asset2.computeProfit(), asset1.computeProfit());
    }
}
