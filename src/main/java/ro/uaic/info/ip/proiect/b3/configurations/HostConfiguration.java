package ro.uaic.info.ip.proiect.b3.configurations;

public class HostConfiguration {
    private String name;

    public HostConfiguration() {
        this.name = "http://localhost:8080/";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
