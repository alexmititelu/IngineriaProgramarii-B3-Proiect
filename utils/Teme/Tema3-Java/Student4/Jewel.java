package asset;

/**
 * clasa mostenita din item
 */
public class Jewel extends Item {
    /**
     * constructor
     * @param name nume
     * @param price pret
     */
    public Jewel(String name, double price) {
        super(name, price);
    }

    @Override
    public String toString() {
        return "Jewel {\n" +
                "name: " + getName() + "," +
                "\nprice: " + getPrice() +
                "\n}";
    }
}
