package org.example;


import org.example.GlobalClass.UserAccount;

import java.sql.*;

import java.util.Scanner;

public class ForgotPassword {
    static final String url = "jdbc:mysql://localhost:3306/mti_simple_banking_system";
    //    static final String url = "jdbc:mysql://localhost:3306/mti_db";
    static final String username = "root";
    static final String password = "";
    private String uName;
    private String pWord;
    private String newPassword;
    private String confirmNewPassword;
    Scanner scan = new Scanner(System.in);


    public void forgotPassword(){
        System.out.println("-------------------------------");
        System.out.println("        FORGOT PASSWORD        ");
        System.out.println("-------------------------------");

        System.out.print("Enter Username: ");
        uName = scan.nextLine();

        checkUsernameDB();

        enterNewPassword();

        String[] newArgs = {""};
        Main.main(newArgs);
    }

    public void checkUsernameDB() {
        String selectSpecificRecordQuery = "SELECT * FROM users_account WHERE USERNAME = '" + uName + "';";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement statement = conn.createStatement()){

            ResultSet results = statement.executeQuery(selectSpecificRecordQuery);

            if (results.next()){
                pWord = results.getString("PASSWORD");

            }else {
                System.out.println("No record found.");
                forgotPassword();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void enterNewPassword(){
        newPassword = "";
        confirmNewPassword = "";
        System.out.print("New Password: ");
        newPassword = scan.nextLine();

        System.out.print("Confirm New Password: ");
        confirmNewPassword = scan.nextLine();

        if (!newPassword.equals(confirmNewPassword)){
            System.out.println("New Password and Confirm New Password don't match");
            enterNewPassword();
        }else {
            changePassword();
        }
    }

    public void changePassword(){

            String updateDepositAmount = "UPDATE users_account SET PASSWORD = ? WHERE USERNAME = '" + uName + "';";

            try (Connection con = DriverManager.getConnection(url, username, password);
                 PreparedStatement preparedStatement = con.prepareStatement(updateDepositAmount)){

                preparedStatement.setString(1, newPassword);

                preparedStatement.executeUpdate();


            } catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("Password Updated Successfully.");

    }

}
