package ro.uaic.info.ip.proiect.b3.configurations;

public class DatabaseConfiguration {
    private String connectionUrl;
    private String username;
    private String password;

    public DatabaseConfiguration() {
        this.connectionUrl = "jdbc:mysql://160.153.18.88/teodor";
        this.username = "proca_teodor";
        this.password = "proiectip";
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

