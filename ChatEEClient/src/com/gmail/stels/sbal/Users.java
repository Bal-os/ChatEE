package com.gmail.stels.sbal;

import com.gmail.stels.sbal.jsonable.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Users extends WebClass{
    private static final Users users = new Users();
    private static final String path = "/users";

    private List<User> list = new ArrayList<>();

    public static Users getInstance() {
        return users;
    }

    private void update(){
        URL url = null;
        String strBuf = null;
        try {
            url = new URL(Utils.getURL() + path);
            strBuf = getResponseStr(url);
            JsonUsers newJson = gson.fromJson(strBuf, JsonUsers.class);
            if(newJson != null)
                this.list = newJson.getList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<User> getItems(boolean isUsers){
        update();
        List<User> rooms = new ArrayList<>();
        List<User> users = new ArrayList<>();
        for(User user: list){
            if(MyParser.isRoom(user.getLogin())){
                rooms.add(user);
            }
            else{
                users.add(user);
            }
        }
        if(isUsers) return Collections.unmodifiableList(users);
            else return Collections.unmodifiableList(rooms);
    }

    private List<User> getRooms(){
        return getItems(false);
    }

    private List<User> getUsers(){
        return getItems(true);
    }

    public void printUsers() {
        for(User user: getUsers()){
            System.out.println(user);
        }
    }

    public void printRooms() {
        for(User user: getRooms()){
            System.out.println(user);
        }
    }

    public boolean isRoomExist(String login){
        if(MyParser.isRoom(login)) {
            for (User user : getRooms()) {
                if (user.getLogin().equals(login)) return true;
            }
        }
        return false;
    }

    public User getRoom(String login){
        if(MyParser.isRoom(login)) {
            for (User user : getRooms()) {
                if (user.getLogin().equals(login)) return user;
            }
        }
        return null;
    }

    public User getUser(String login){
        System.out.println(login);
        if(login != null && !MyParser.isRoom(login)) {
            for (User user : getUsers()) {
                if (login.equals(user.getLogin())) return user;
            }
        }
        return null;
    }
}
