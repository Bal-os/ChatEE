package com.gmail.stels.sbal.servlets;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

public abstract class MyServlet extends HttpServlet {

    protected synchronized byte[] requestBodyToArray(HttpServletRequest req) throws IOException {
        InputStream is = req.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }

    protected synchronized void doGetSendJson(HttpServletResponse resp, String json) throws IOException{
        if (json != null) {
            resp.setContentType("application/json");
            PrintWriter pw = resp.getWriter();
            pw.print(json);
        }
    }

    protected synchronized boolean isRequestBad(Object object, HttpServletResponse resp){
        boolean result = (object == null);
        if(result)
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return result;
    }

    protected synchronized String getBufStr(HttpServletRequest req) throws IOException{
        byte[] buf = requestBodyToArray(req);
        return new String(buf, StandardCharsets.UTF_8);
    }

}
