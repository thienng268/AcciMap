package com.example.accimap.models;

public class UserSingleton {
    private String userName = "";

    // Getter/setter

    private static UserSingleton instance;

    public static UserSingleton getInstance() {
        if (instance == null)
            instance = new UserSingleton();
        return instance;
    }
    private UserSingleton() { }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String newValue) {
        this.userName = newValue;
    }
}
