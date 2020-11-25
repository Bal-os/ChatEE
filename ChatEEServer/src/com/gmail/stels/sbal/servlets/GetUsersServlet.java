package com.gmail.stels.sbal.servlets;

import com.gmail.stels.sbal.UserList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetUsersServlet extends MyServlet{
    private UserList userList = UserList.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println(userList.toString());
        String json = userList.toJSON();

        doGetSendJson(resp, json);
    }
}
