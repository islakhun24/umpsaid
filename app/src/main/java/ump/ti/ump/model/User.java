package ump.ti.ump.model;

public class User {
    public String username;
    public String email;
    public String level;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String level) {
        this.username = username;
        this.email = email;
        this.level = level;
    }
}
