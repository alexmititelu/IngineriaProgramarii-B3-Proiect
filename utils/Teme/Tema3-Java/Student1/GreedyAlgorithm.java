package compulsory;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GreedyAlgorithm implements IAlgorithm {
    @Override
    public List<IAsset> algorithm(List<IAsset> listAssets, int money) {
        List<IAsset> assetsBuy = new ArrayList<>();
        Collections.sort(listAssets, new Comparator<IAsset>() {
            @Override
            public int compare(IAsset o1, IAsset o2) {
                if (o1.financialRisk() < o2.financialRisk()) {
                    return 1;
                }
                if (o1.financialRisk() > o2.financialRisk()) {
                    return -1;
                }
                return 0;
            }
        });

        for (IAsset contor : listAssets) {
            if (contor instanceof Item) {
                if (money - ((Item) contor).getPrice() >= 0) {
                    money -= ((Item) contor).getPrice();
                    assetsBuy.add(contor);
                }
            }
        }
        return assetsBuy;
    }
}
