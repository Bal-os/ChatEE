package com.gmail.stels.sbal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.Objects;

public class User implements Jsonable{
    @Expose
    private String login;

    @Expose
    private boolean isOnline;

    @Expose(serialize = false)
    private String password;
    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.isOnline = true;
    }

    public synchronized boolean isPassword(User other){
        return this.password.equals(other.password);
    }

    public synchronized String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public synchronized boolean isOnline() {
        return isOnline;
    }

    public synchronized void setOnline(boolean online) {
        isOnline = online;
    }

    @Override
    public String toJSON() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static User fromJSON(String s) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(s, User.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("[").append(login)
                .append("] ")
                .toString();
    }
}
