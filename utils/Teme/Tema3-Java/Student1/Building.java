package compulsory;

public class Building extends Item implements IAsset {

    private int area;

    public Building(String name,int area, int price) {
        this.name=name;
        this.area=area;
        this.price=price;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public int getPrice(){
        return this.price;
    }

    public void setPrice(int price){
        this.price=price;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public float computeProfit(){
        return area/price;
    }
}
