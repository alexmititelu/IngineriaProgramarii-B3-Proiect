
public class Project {
    String name;
    int capacity;

    public Project(String name, int capacity)
    {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Project name: " + this.name + '\n' + "Project capacity: " + capacity + '\n';
    }


}
