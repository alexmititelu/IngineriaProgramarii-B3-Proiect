package MainPackage;

import Interfaces.Asset;

import java.util.ArrayList;
import java.util.List;

public class Portofolio {
    private List<Asset> result;

    public Portofolio() {
        this.result = new ArrayList<>();
    }
    public List<Asset> getResult() {
        return result;
    }

    public void addOneAsset(Asset asset) {
        this.result.add(asset);
    }
    public void setResult(List<Asset> result) {
        this.result = result;
    }

    @Override
    public String toString(){
        return result.toString();
    }
}
