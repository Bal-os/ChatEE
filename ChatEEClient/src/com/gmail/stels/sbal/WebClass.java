package com.gmail.stels.sbal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public abstract class WebClass {
    protected final Gson gson;

    public WebClass(){
        gson = new GsonBuilder().create();
    }

    protected void checkRequest(int req){
        if (req != 200) { // 200 OK
            System.out.println("HTTP error occured: " + req);
            System.exit(0);
        }
    }

    protected synchronized String getResponseStr(URL url) throws IOException {
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        InputStream is = http.getInputStream();
        byte[] buf = responseBodyToArray(is);
        return new String(buf, StandardCharsets.UTF_8);
    }

    protected byte[] responseBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }
}
