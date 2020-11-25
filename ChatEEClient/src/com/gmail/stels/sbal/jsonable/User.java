package com.gmail.stels.sbal.jsonable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class User extends sendFuntion implements Jsonable{
    private String login;
    private boolean isOnline;
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.isOnline = true;
    }

    public String getPassword(){return password;}

    public synchronized boolean isPassword(User other){
        return this.password.equals(other.password);
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
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
