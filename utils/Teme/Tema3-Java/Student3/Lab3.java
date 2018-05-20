package lab3;

public class Lab3 {
/**
 * initializare
 * @param args 
 */
    public static void main(String[] args) {
        Building h1 = new Building("House 1", 27, 9);
        Building h2 = new Building("House 2", 27, 9);
        Building h3 = new Building("House 3", 64, 16);
        Vehicle c1 = new Vehicle("Car 1", 8, 4);
        Vehicle c2 = new Vehicle("Car 2", 8, 4);
        Jewel j1 = new Jewel("Diamond", 2);

        AssetManager manager = new AssetManager();

        manager.add(h1, h2, h3);
        manager.add(c1, c2); 
        System.out.println("Items sorted by name: " + manager.getItems());
    }
    
}
