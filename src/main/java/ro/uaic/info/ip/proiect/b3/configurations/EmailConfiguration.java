package ro.uaic.info.ip.proiect.b3.configurations;

public class EmailConfiguration {
    private String address;
    private String password;

    public EmailConfiguration() {
        this.address = "uaic.contact.elearning@gmail.com";
        this.password = "ContactElearning123";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
