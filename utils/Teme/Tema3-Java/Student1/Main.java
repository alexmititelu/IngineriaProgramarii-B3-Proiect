package compulsory;

public class Main {

    public static void main(String args[])
    {
        Building h1=new Building("House 1", 27,9);
        Building h2=new Building("House 2", 27,9);
        Building h3=new Building("House 3", 64,16);

        Vehicle c1=new Vehicle("Car 1", 8,4);
        Vehicle c2=new Vehicle("Car 2", 8,4);
        System.out.println("compute: "+ h3.financialRisk());

        Jewel ring=new Jewel("Gold Diamond Ring",2);

        AssetManager manager=new AssetManager();
        manager.add(h1,h2,h3);
        manager.add(c1,c2);
        manager.add(ring);

        System.out.println("Items sorted by their name:\n"+ manager.getItems());

        System.out.println("Assets sorted by profit: " + manager.getAssets());


        int maxValue = 20;
        Portofolio solution = manager.createPortofolio(new GreedyAlgorithm(), maxValue);
        System.out.println("The best portofolio: " + solution);
    }
}
