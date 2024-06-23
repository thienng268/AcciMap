package com.example.accimap.models;

public class User {
    private String uId;
    private String email;
    private String avatar;

    // Default constructor required for Firebase
    public User() {
    }

    public User(String uId, String email, String avatar) {
        this.uId = uId;
        this.email = email;
        this.avatar = avatar;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
