package main.java.model;

public class User {
    private String id;
    private String name;
    private String password;
    private String email;

    public User(String id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getInfo() {
        return String.format("ID: %s, Name: %s, Email: %s", id, name, email);
    }
}
