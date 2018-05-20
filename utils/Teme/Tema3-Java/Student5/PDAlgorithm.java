/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import Interfaces.Algorithm;
import Interfaces.Asset;
import MainPackage.Portofolio;
import java.util.List;

/**
 *
 * @author svitel
 */
public class PDAlgorithm implements Algorithm {
    @Override
    public Portofolio solveAlgorithm(List<Asset> assetList, int maxValue) {
        int[] Optim = new int[maxValue + 1];
        int i, j, sol;
        
        Portofolio result = new Portofolio();
        
        Optim[0] = 0;
        sol = 0;
        
        for(i = 0; i < assetList.size(); i++)
        for(j = (int)maxValue - (int)assetList.get(i).getPrice(); j >= 0; j--) {
            if( Optim[j+(int)assetList.get(i).getPrice()] < Optim[j] + assetList.get(i).getPrice())
            {
                Optim[j+(int)assetList.get(i).getPrice()] = Optim[j] + (int)assetList.get(i).getPrice();
                if( Optim[j + (int)assetList.get(i).getPrice()] > sol)
                {
                    sol = Optim[j + (int)assetList.get(i).getPrice()];
                    result.addOneAsset(assetList.get(i));
                }
            }
        }
        return result;
    }
}
