package edu.virginia.cs.gui;

public class UserName {
    private static UserName instance = null;
    private String username;
    protected UserName() {}
    public static UserName getInstance() {
        if (instance == null) {
            instance = new UserName();
        }
        return instance;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
