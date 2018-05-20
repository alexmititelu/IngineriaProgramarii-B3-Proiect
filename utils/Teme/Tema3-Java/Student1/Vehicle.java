package compulsory;

public class Vehicle extends Item implements IAsset {

    private int performance;

    public Vehicle(String name,int performance, int price) {
        this.name=name;
        this.performance=performance;
        this.price=price;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public int getPrice(){
        return this.price;
    }

    public void setPrice(int price){
        this.price=price;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public float computeProfit(){
        return performance/price;
    }


}
