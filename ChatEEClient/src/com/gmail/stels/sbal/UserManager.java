package com.gmail.stels.sbal;

import com.gmail.stels.sbal.jsonable.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public abstract class UserManager extends WebClass{
    protected final Scanner scanner;
    protected String path;

    public UserManager(String path) {
        scanner = new Scanner(System.in);
        this.path = Utils.getURL() + path;
    }

    protected abstract User getUser();

    protected int sendUser(String login, String password) throws IOException {
        User u = new User(login, password);
        int res = u.send(this.path);

        return res;
    }

    protected loginCode getStatus() {
        String responseStr;
        loginCode result = loginCode.WRONG_PASSWORD;
        try {
            responseStr = getResponseStr(new URL(path));

            String response = gson.fromJson(responseStr, String.class);
            if (response != null)
                result = loginCode.valueOf(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
