package com.example.SeleniumJavaTest;

public class User {

    private String bilkent_id;
    private String password;

    public User(String bilkent_id, String password){
        this.bilkent_id = bilkent_id;
        this.password = password;
    }

    public String getUsername() {
        return bilkent_id;
    }

    public String getPassword() {
        return password;
    }
}
