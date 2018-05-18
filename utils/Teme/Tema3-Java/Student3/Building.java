package lab3;
/**
 * extinde Item
 * @author Aurelia
 */
public class Building extends Item implements Asset {
    private int area;
/**
 * 
 * @param name
 * @param area
 * @param price 
 */
    public Building(String name, int area, double price) {
        super(name, price);
        this.area = area;
    }
/**
 * 
 * @return area
 */
    public int getArea() {
        return area;
    }

    @Override
    public double computeProfit() {
        return area/getPrice();
    }

    @Override
    public String toString() {
        return "Building {" + "\nname: " + getName() + "," + "\nprice: " + getPrice() + "," + "\narea: " + area + "\n}";
    }
}
