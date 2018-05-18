package asset;

/**
 * clasa mostenita din item
 */
public class Vehicle extends Item implements Asset {
    private int performance;

    /**
     * constructor
     * @param name numele
     * @param performance performanta
     * @param price pretul
     */
    public Vehicle(String name, int performance, double price) {
        super(name, price);
        this.performance = performance;
    }

    /**
     * preia performanta
     * @return performanta
     */
    public int getPerformance() {
        return performance;
    }

    @Override
    public double computeProfit() {
        return performance / getPrice();
    }

    @Override
    public double riskFactor() {
        double price = this.getPrice();

        if (price == 0) {
            return 0;
        }

        return computeProfit() / getPrice();
    }

    @Override
    public String toString() {
        return "Vehicle {" +
                "\nname: " + getName() + "," +
                "\nprice: " + getPrice() + "," +
                "\nperformance: " + performance +
                "\n}";
    }
}
