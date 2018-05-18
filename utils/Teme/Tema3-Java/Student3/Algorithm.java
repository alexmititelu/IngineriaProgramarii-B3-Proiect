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
import java.util.List;

public interface Algorithm {
    /**
     * Orders assets according to strategy:
     * @param assets the list of assets to be ordered
     */
    void orderAssetsAccordingToStrategy(List <Asset> assets);
}
