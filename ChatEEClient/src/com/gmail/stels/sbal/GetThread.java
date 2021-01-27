package com.gmail.stels.sbal;

import com.gmail.stels.sbal.jsonable.JsonMessages;
import com.gmail.stels.sbal.jsonable.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetThread extends WebClass implements Runnable {
    private final String pathTemplate = "&to=";
    private String path = "";
    private int n;

    public GetThread() {
        super();
    }

    public GetThread(String userName) {
        super();
        path = pathTemplate + userName;
    }

    @Override
    public void run() {
        try {
            while ( ! Thread.interrupted()) {
                URL url = new URL(Utils.getURL() + "/get?from=" + n + path);
                    String strBuf = getResponseStr(url);
                    JsonMessages list = gson.fromJson(strBuf, JsonMessages.class);
                    if (list != null) {
                        for (Message m : list.getList()) {
                            System.out.println(m);
                            n++;
                        }
                    }

                Thread.sleep(500);
            }
        } catch (Exception ex) {
        }
    }
}
