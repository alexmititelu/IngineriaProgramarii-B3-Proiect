package compulsory;

import java.util.Objects;

public abstract class Item {

    protected String name;
    protected int price;

    @Override
    public String toString() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
