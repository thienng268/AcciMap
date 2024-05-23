package com.example.accimap.models;

public class Account {
    public String aUsername;
    public String aPassword;
    public String aPasscode;

    public String getaUsername() {
        return aUsername;
    }

    public void setaUsername(String aUsername) {
        this.aUsername = aUsername;
    }

    public String getaPassword() {
        return aPassword;
    }

    public void setaPassword(String aPassword) {
        this.aPassword = aPassword;
    }

    public String getaPasscode() {
        return aPasscode;
    }

    public void setaPasscode(String aPasscode) {
        this.aPasscode = aPasscode;
    }

    public Account(String aUsername, String aPassword, String aPasscode) {
        this.aUsername = aUsername;
        this.aPassword = aPassword;
        this.aPasscode = aPasscode;
    }
}
