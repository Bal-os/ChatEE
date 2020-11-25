package com.gmail.stels.sbal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class UserList implements Jsonable{
    private static final UserList userList = new UserList();

    private final Gson gson;
    private final List<User> list = new ArrayList<>();

    public static UserList getInstance() {
        return userList;
    }

    private UserList() {
        gson = new GsonBuilder().create();
    }

    public synchronized boolean isUser(User user) {
        return list.contains(user);
    }

    public synchronized boolean isPassword(User user){
        int indexOfEqualUser = list.indexOf(user);
        if(indexOfEqualUser >= 0){
            User userInList = list.get(indexOfEqualUser);
            if(userInList.isPassword(user)) return true;
        }
        return false;
    }

    public synchronized boolean add(User user) {
        if( ! isUser(user)){
            list.add(user);
            return true;
        }
        return false;
    }

    @Override
    public synchronized String toJSON() {
        return gson.toJson(new JsonUsers(list));
    }

    @Override
    public String toString() {
        return "UserList{" +
                "list=" + list +
                '}';
    }
}
