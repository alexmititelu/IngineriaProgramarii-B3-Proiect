package asset;

/**
 * clasa mostenita din item
 */
public class Building extends Item implements Asset {
    private int area;

    /**
     * constructor
     * @param name numele
     * @param area aria
     * @param price pretul
     */
    public Building(String name, int area, double price) {
        super(name, price);
        this.area = area;
    }

    /**
     * preia aria
     * @return aria
     */
    public int getArea() {
        return area;
    }

    @Override
    public double computeProfit() {
        return area / getPrice();
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
        return "Building {" +
                "\nname: " + getName() + "," +
                "\nprice: " + getPrice() + "," +
                "\narea: " + area +
                "\n}";
    }
}
