/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab3;

/**
 *
 * @author Aurelia
 */
import lab3.asset.Asset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class GreedyAlgorithm implements Algorithm {
    private static final Comparator <Asset> ASSET_COMPARATOR = new AssetComparator();

    /**
     * Sorts descending the specified list according to the profit.
     *
     * @param assets the list of assets to be ordered
     */
    @Override
    public void orderAssetsAccordingToStrategy(List <Asset> assets) {
        assets.sort(ASSET_COMPARATOR);
        Collections.reverse(assets);
    }
}
