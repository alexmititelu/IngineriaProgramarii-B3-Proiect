package items;

import Interfaces.Asset;

public class Building extends Item implements Asset {
    private long area;

    public Building(String name, double price,long area) {
        super(name, price);
        this.area=area;
    }
    @Override
    public double computeProfit() {
        return area/price;
    }
    @Override
    public double getPrice() {
        return this.price;
    }

}
