package compulsory;

import java.util.*;

public class RandomAlgorithm implements IAlgorithm {
    @Override
    public List<IAsset> algorithm(List<IAsset> listAssets, int money) {
        List<IAsset> assetsBuy = new ArrayList<>();
        boolean indicator[] = new boolean[listAssets.size()];

        for(int i=0;i< indicator.length;i++){
            indicator[i] = false;
        }

        while(true){
            int idx = new Random().nextInt(indicator.length);
            if(indicator[idx] == false){
                if(listAssets.get(idx) instanceof Item){
                    if(money - ((Item) listAssets.get(idx)).getPrice() >= 0){
                        assetsBuy.add(listAssets.get(idx));
                        money -= ((Item) listAssets.get(idx)).getPrice();
                    }
                }

                indicator[idx] = true;

                if(isFull(indicator)){
                    break;
                }
            }

        }

        return assetsBuy;
    }

    private boolean isFull(boolean indicator[]){
        for(int i=0;i<indicator.length;i++){
            if(indicator[i] == false){
                return false;
            }
        }
        return true;
    }
}
