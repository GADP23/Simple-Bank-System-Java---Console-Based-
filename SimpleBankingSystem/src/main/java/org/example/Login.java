package org.example;

import org.example.GlobalClass.UserAccount;

import java.io.BufferedReader;

import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class Login{
    static final String url = "jdbc:mysql://localhost:3306/mti_simple_banking_system";
    //    static final String url = "jdbc:mysql://localhost:3306/mti_db";
    static final String username = "root";
    static final String password = "";
    static Scanner scan = new Scanner(System.in);
    String uName;
    String pWord;
    String accNum;
    String accRole;
    Date depositDate;
    Date withdrawDate;
    float amount;

    public UserAccount loginCredentials()  {
        System.out.println("-------------------------------");
        System.out.println("        OROBLOBIS BANK         ");
        System.out.println("-------------------------------");


        System.out.print("Username: ");
        uName = scan.nextLine();

        System.out.print("Password: ");
        pWord = scan.nextLine();

        if (uName != null && pWord != null){
            String selectUsernameQuery = "SELECT * FROM account ac LEFT JOIN users_account ua ON ua.USERNAME = ac.USERNAME WHERE ua.USERNAME = '" + uName + "' AND ua.PASSWORD = '" + pWord + "';";

            try (Connection conn = DriverManager.getConnection(url, username, password);
                 Statement statement = conn.createStatement()){

                ResultSet results = statement.executeQuery(selectUsernameQuery);

                if (results.next()){
                    accRole = results.getString("ACCESS_ROLE");
                }else {
                    System.out.println("Incorrect Username/Password.");
                    miniForgotPassMenu();
                }

            } catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(accRole);
            return new UserAccount(uName, pWord, accNum, accRole, depositDate, withdrawDate, amount);
        }else {
            System.out.println("Invalid Username/Password.");
            return null;
        }

    }

    public void miniForgotPassMenu(){
        System.out.println("\n[1] Forgot Password\n[2] Back to Login");
        byte userInput = Byte.parseByte(scan.nextLine());

        if (userInput == 1){

            ForgotPassword forgotPassword = new ForgotPassword();
            forgotPassword.forgotPassword();

        } else if (userInput == 2){
            loginCredentials();
        }else {
            System.out.println("The menu you wish to access does not exist.");
            miniForgotPassMenu();
        }
    }

}


