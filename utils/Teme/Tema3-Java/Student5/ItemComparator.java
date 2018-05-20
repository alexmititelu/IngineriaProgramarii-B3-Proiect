package Comparators;

import items.Item;

import java.util.Comparator;

public class ItemComparator implements Comparator<Item> {

    @Override
    public int compare(Item item1, Item item2) {
        return item1.getName().compareTo(item2.getName());
    }
}
