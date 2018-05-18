package lab3;
/**
 * extinde Item
 * @author Aurelia
 */
public class Jewel extends Item{
    public Jewel(String name, double price) {
    super(name, price);
    }

    @Override
    public String toString() {
        return "Jewel {\n" + "name: " + getName() + "," + "\nprice: " + getPrice() + "\n}";
    }
}
