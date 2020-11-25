package com.gmail.stels.sbal.servlets;

import com.gmail.stels.sbal.Message;
import com.gmail.stels.sbal.MessageList;
import com.gmail.stels.sbal.User;
import com.gmail.stels.sbal.UserList;
import com.google.gson.Gson;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddServlet extends MyServlet {

    private MessageList msgList = MessageList.getInstance();
    private UserList userList = UserList.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String bufStr = getBufStr(req);
        String itemStr = req.getParameter("item");


        if(!isRequestBad(itemStr, resp)){

            switch (itemStr){
                case "user":

                    User user = User.fromJSON(bufStr);
                    if (!isRequestBad(user, resp))
                        userList.add(user);
                    break;

                case "message":

                    Message msg = Message.fromJSON(bufStr);
                    if (!isRequestBad(msg, resp))
                        msgList.add(msg);
                    break;

                default:
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
