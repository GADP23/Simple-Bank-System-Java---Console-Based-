package org.example;

import org.example.GlobalClass.UserAccount;

import java.util.Date;

public class Main {

    public static void main(String[] args){
        Login login = new Login();
        UserAccount userAccount = login.loginCredentials();
        MainMenu mainMenu = new MainMenu(userAccount);

        if (userAccount.getAccRole().equals("Admin")){
            mainMenu.mainMenuAdminModule();

        } else if (userAccount.getAccRole().equals("Client")) {
            mainMenu.mainMenuClientModule();
        }else {
            System.out.println("Invalid Username/Password.");
            String[] newArgs = {""};
            Main.main(newArgs);
        }
    }

}