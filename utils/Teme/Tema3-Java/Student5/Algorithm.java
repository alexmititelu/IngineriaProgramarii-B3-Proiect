package Interfaces;
import MainPackage.Portofolio;

import java.util.List;

public interface Algorithm {
    Portofolio solveAlgorithm(List<Asset> assetList,int maxValue);
}
