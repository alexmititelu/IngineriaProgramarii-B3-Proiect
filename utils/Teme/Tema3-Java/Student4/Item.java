package asset;

import java.util.Objects;

/**
 * superclasa pentru iteme
 */
public class Item {
    private String name;
    private double price;

    /**
     * constructor implicit
     * @param name numele itemului
     * @param price pretul itemului
     */
    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    /**
     * afiseaza numele
     * @return numele
     */
    public String getName() {
        return name;
    }

    /**
     * seteaza numele
     * @param name numele de setat
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * preiau pretul
     * @return pretul
     */
    public double getPrice() {
        return price;
    }

    /**
     * seteaza pretul
     * @param price pretul
     */
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(item.price, price) == 0 &&
                Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }


}
