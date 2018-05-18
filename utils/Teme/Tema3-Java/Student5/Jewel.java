package items;

public class Jewel extends Item{

    public Jewel(String name, double price) {
        super(name, price);
    }
    @Override
    public double getPrice() {
        return this.price;
    }
}
