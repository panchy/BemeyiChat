package com.yido.bemeyichat.model;

/**
 * Created by Panch on 23.04.2016.
 */
public class user {

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String username;
    private String password;
    private long id;

}
