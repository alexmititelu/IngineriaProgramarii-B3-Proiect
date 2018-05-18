package info;

public class Manual extends Item {

    public Manual(String name, String path) {
        super(name, path);
    }

    @Override
    public String toString() {
        return "Manual{" +
                "name: " + this.getName() +
                "path: " + this.getPath() +
                "}";
    }
}
