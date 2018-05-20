package items;

import Interfaces.Asset;

public class Vehicle extends Item implements Asset {
    private long performance;

    public Vehicle(String name, double price,long performance) {
        super(name, price);
        this.performance=performance;
    }

    @Override
    public double computeProfit() {
        return this.performance/this.price;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

}
