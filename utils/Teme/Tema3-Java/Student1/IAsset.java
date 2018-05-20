package compulsory;

public interface IAsset {

    float computeProfit();
    default float financialRisk(){
        float risk;
        risk = 1/this.computeProfit();
        return risk;
    }

}
