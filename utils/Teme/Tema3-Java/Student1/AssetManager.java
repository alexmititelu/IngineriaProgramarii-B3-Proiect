package compulsory;

import java.util.*;

public class AssetManager {

    private List<Item>itemList=new ArrayList<>();

    public void add(Item...item){
        for(Item itemCounter:item)
        {
            itemList.add(itemCounter);
        }

    }

    public List<Item>getItems(){
        Collections.sort(this.itemList, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        return this.itemList;
    }

    public List<IAsset> getAssets(){
        List<IAsset> assets = new ArrayList<>();
        for (Item contor:this.itemList){
            if(contor instanceof IAsset){
                assets.add((IAsset) contor);
            }

            Collections.sort(assets, new Comparator<IAsset>() {
                @Override
                public int compare(IAsset o1, IAsset o2) {
                    if(o1.computeProfit() == o2.computeProfit()){
                        return 0;
                    }
                    if(o1.computeProfit() < o2.computeProfit()){
                        return -1;
                    }
                    if(o1.computeProfit() > o2.computeProfit()){
                        return 1;
                    }
                    return 0;
                }
            });
        }

        return assets;
    }


    public Portofolio createPortofolio (IAlgorithm algorithm, int maxValue){
        Portofolio portofolio = new Portofolio();
        for(IAsset contor: algorithm.algorithm(this.getAssets(), maxValue)){
            portofolio.addItem(contor);
        }

        return portofolio;
    }

}
