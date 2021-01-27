package com.gmail.stels.sbal;

import com.gmail.stels.sbal.jsonable.User;

import java.io.IOException;

public class Logining extends UserManager {

    public Logining() {
        super("/login");
    }

    @Override
    synchronized protected User getUser(){
        User user;
        loginCode currStatus;

        do {
            user = this.enter();
            currStatus = this.getStatus();
            if(currStatus.equals(loginCode.WRONG_PASSWORD)) {
                System.out.println("Wrong password, try again");
            }
            else
            if(currStatus.equals(loginCode.WRONG_USER)) {
                System.out.println("No such user");
                System.out.println("Do you want to registration?" + "\n" + "Y\\N");
                String isReg = scanner.nextLine();

                if(isReg.toUpperCase().equals("Y")) {
                    user = new Registration().getUser();
                    if(user != null) break;
                }
            }
        } while(!currStatus.equals(loginCode.OK));

        return user;
    }

    private User enter(){
        System.out.println("Enter your login: ");
        String login = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        try {
            checkRequest(sendUser(login, password));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new User(login, password);
    }
}