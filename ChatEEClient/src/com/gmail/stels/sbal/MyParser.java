package com.gmail.stels.sbal;

public class MyParser {
    private final static String commandStart = "/";
    private final static String nickStart = "@";
    private final static String trimmer = " ";
    private final static String roomKey = "Room";

    public static String parseCommand(String text){
        text = text.trim();
        if(!text.contains(commandStart)) return null;
        int n = text.indexOf(commandStart);
        text += trimmer;
        String res = text.substring(n + 1, text.indexOf(trimmer, n));
        return res.trim();
    }
    public static String parseText(String text){
        text = text.trim();
        if(text == null) return null;
        int n = text.indexOf(nickStart);
        if(n < 0) return text;
        text += trimmer;
        String res = text.substring(text.indexOf(trimmer) + 1);
        return res.trim();
    }
    public static String parseNick(String text){
        text = text.trim();
        if(!text.contains(nickStart)) return null;
        int n = text.indexOf(nickStart);
        text += trimmer;
        String res = text.substring(n + 1, text.indexOf(trimmer, n));
        return res.trim();
    }
    public static boolean isRoom(String name){
        return name.endsWith(roomKey);
    }
}
