package com.gmail.stels.sbal.servlets;

import com.gmail.stels.sbal.User;
import com.gmail.stels.sbal.UserList;
import com.gmail.stels.sbal.loginCode;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LogInServlet extends MyServlet {
    private UserList userList = UserList.getInstance();
    protected String answer;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String bufStr = getBufStr(req);

        User user = User.fromJSON(bufStr);
        if (!isRequestBad(user, resp)) {
            String res = loginCode.OK.toString();
            if(! userList.isUser(user)){
                res = loginCode.WRONG_USER.toString();
            }
            else if(! userList.isPassword(user)){
                res = loginCode.WRONG_PASSWORD.toString();
            }
            answer = res;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String json = new Gson().toJson(answer);

        doGetSendJson(resp, json);
    }
}
