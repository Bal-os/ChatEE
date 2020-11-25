package com.gmail.stels.sbal;

import com.gmail.stels.sbal.jsonable.Message;
import com.gmail.stels.sbal.jsonable.User;

import java.io.IOException;
import java.util.Scanner;

public class Session extends WebClass{
    private static final Session session = new Session();
    private final Users users = Users.getInstance();
    private final Scanner scanner;
    private final Thread[] threads;
    private User user;
    private User room;

    private enum MyThreads{
        USER_THREAD,
        ROOM_THREAD,
        GLOBAL_TREAD;
    }
    private enum Commands {
        exit,
        login,
        reg,
        users,
        rooms,
        leave,
        offline,
        online,
        check,
        make,
        commands,
        come;
    }

    public static Session createNewSession() {
        return session;
    }

    private Session() {
        scanner = new Scanner(System.in);
        threads = new Thread[MyThreads.values().length];
        user = new Logining().getUser();
        this.printCommands();
        this.start();
    }

    private void start(){
        runThread(MyThreads.GLOBAL_TREAD);
        runThread(MyThreads.USER_THREAD);

        System.out.println("Enter your message: ");
        while (true) {
            String text = scanner.nextLine();
            if (text.isEmpty()) continue;
            parser(text);
        }
    }

    private void parser(String text){
        String command = MyParser.parseCommand(text);
        String user = MyParser.parseNick(text);
        boolean isUser = user != null;
        boolean isComm = command != null;
        if(isComm){
            try {
                switch (Commands.valueOf(command)) {
                    case login:
                        this.user = new Logining().getUser();
                        break;
                    case reg:
                        this.user = new Registration().getUser();
                        break;
                    case leave:
                        leaveRoom();
                        break;
                    case users:
                        users.printUsers();
                        break;
                    case rooms:
                        users.printRooms();
                        break;
                    case online:
                        this.user.setOnline(true);
                        break;
                    case offline:
                        this.user.setOnline(false);
                        break;
                    case exit:
                        System.exit(0);
                        break;
                    case commands:
                        printCommands();
                        break;
                    case come:
                        if(!isUser) throw new IllegalArgumentException();
                        comeRoom(user);
                        break;
                    case make:
                        if(!isUser) throw new IllegalArgumentException();
                        makeRoom(user);
                        break;
                    case check:
                        if(!isUser) throw new IllegalArgumentException();
                        checkUser(user);
                        break;
                    default:
                        System.out.println("Wrong command");
                }
            } catch (IllegalArgumentException e){
                System.out.println("Wrong command");
            }
            return;
        }
        if(user == null && this.room != null){
            sendMessage(this.room.getLogin(), MyParser.parseText(text));
        }
        else sendMessage(user, MyParser.parseText(text));
    }

    private void printCommands(){
        System.out.println("list of available commands");
        for (Commands comm: Commands.values()) {
            System.out.println('/' + comm.toString());
        }
    }

    private void makeRoom(String name){
        if(MyParser.isRoom(name)) {
            room = new Registration().makeRoom(name);
            this.comeRoom(room.getLogin());
        } else
            System.out.println("Wrong name for room");
    }

    private void checkUser(String name){
        User user = users.getUser(name);
        if(user == null) System.out.println("No such user");
        else
            if(user.isOnline()) System.out.println("Is online");
            else System.out.println("Is offline");
    }

    private void leaveRoom() {
        if(room != null) {
            interruptThread(MyThreads.ROOM_THREAD);
            runThread(MyThreads.GLOBAL_TREAD);
            room = null;
        }
    }

    private void comeRoom(String name){
        if(!users.isRoomExist(name)) System.out.println("Room is not exists");
        else{
            interruptThread(MyThreads.GLOBAL_TREAD);
            room = users.getRoom(name);
            runThread(MyThreads.ROOM_THREAD);
        }
    }

    private void sendMessage(String to, String text) {
        Message m = new Message(user.getLogin(), to, text);
        try {
            int res = m.send(Utils.getURL() + "/add?item=message");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runThread(MyThreads e){
        int n = e.ordinal();
        switch (e){
            case USER_THREAD:
                this.threads[n] = new Thread(new GetThread(user.getLogin()));
                break;
            case ROOM_THREAD:
                this.threads[n] = new Thread(new GetThread(room.getLogin()));
                break;
            case GLOBAL_TREAD:
                this.threads[n] = new Thread(new GetThread());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + e.name());
        }
        this.threads[n].start();
    }

    private void interruptThread(MyThreads e){
        Thread thr = this.threads[e.ordinal()];
        if(thr != null){
            thr.interrupt();
        }
    }

}
