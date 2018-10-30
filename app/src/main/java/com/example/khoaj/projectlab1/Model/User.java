package com.example.khoaj.projectlab1.Model;

public class User {

    String username;
    String name;
    String password;
    String sdt;

    public User(String username, String name, String password, String sdt) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.sdt = sdt;
    }

    public User() {
        // Empty User Constructor
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
