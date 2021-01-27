package com.gmail.stels.sbal;

import com.gmail.stels.sbal.jsonable.User;

import java.io.IOException;

public class Registration extends UserManager {
    static private final String roomPassword = "************";
    Logining log;
    User user = null;

    public Registration(){
        super("/add?item=user");
    }

    private boolean registration() throws IOException{
        System.out.println("Enter your login: ");
        String login = scanner.nextLine();

        if(MyParser.isRoom(login)){
            System.out.println("Not allowed login");
            return false;
        }

        String password;
        String password2;
        do {
            System.out.println("Enter your password twice, please" + "\n" + "first time:");
            password = scanner.nextLine();
            System.out.println("second time:");
            password2 = scanner.nextLine();
        } while(! password.equals(password2));

        checkRequest(log.sendUser(login, password));

        if(log.getStatus() == loginCode.WRONG_USER){
            checkRequest(sendUser(login, password));
            user = new User(login, password);
            return true;
        }
        System.out.println("User with this nickname already exists, please try another nickname");
        return false;
    }

    synchronized protected User makeRoom(String name){
        try {
            checkRequest(sendUser(name, roomPassword));
            user = new User(name, roomPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    synchronized protected User getUser() {
        log = new Logining();
        while(true){
            try {
                if (this.registration()) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}
