package lab3;
/**
 * extinde Item
 * @author Aurelia
 */

public class Vehicle extends Item implements Asset {
    private int performance;
/**
 * 
 * @param name
 * @param performance
 * @param price 
 */
    public Vehicle(String name, int performance, double price) {
        super(name, price);
        this.performance = performance;
    }
/**
 * 
 * @return 
 */
    public int getPerformance() {
        return performance;
    }

    @Override
    public double computeProfit() {
        return performance/getPrice();
    }

    @Override
    public String toString() {
        return "Vehicle {" + "\nname: " + getName() + "," + "\nprice: " + getPrice() + "," + "\nperformance: " + performance + "\n}";
    }
}
