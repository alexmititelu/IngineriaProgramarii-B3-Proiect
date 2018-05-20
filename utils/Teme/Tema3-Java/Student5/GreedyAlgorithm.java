package Algorithms;

import Interfaces.Algorithm;
import Interfaces.Asset;
import MainPackage.Portofolio;

import java.util.List;

public class GreedyAlgorithm implements Algorithm {
    @Override
    public Portofolio solveAlgorithm(List<Asset> assetList, int maxValue) {
        Portofolio result = new Portofolio();
        double range = 1;
        double capacity = 0;
        for (Asset component : assetList) {
            if(capacity+component.getPrice()<maxValue) {
                result.addOneAsset(component);
                capacity = capacity + component.getPrice();
            }
        }
        return result;
    }
}
