package org.example.AdminMainMenu;

import org.example.MainMenu;
import org.example.GlobalClass.UserAccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class CheckCustomerAccountInfo {
    static final String url = "jdbc:mysql://localhost:3306/mti_simple_banking_system";
    //    static final String url = "jdbc:mysql://localhost:3306/mti_db";
    static final String username = "root";
    static final String password = "";
    static Scanner scan = new Scanner(System.in);
    static String accountNumber;
    static String userInputCheckBalMM;

    private UserAccount userAccount;

    public CheckCustomerAccountInfo(UserAccount userAccount){
        this.userAccount = userAccount;
    }

    public void viewCustomerAccInfo(){
        System.out.println("-----------------------------------------");
        System.out.println("    SEARCH CLIENT'S BANK ACCOUNT INFO    ");
        System.out.println("-----------------------------------------");
        System.out.print("\nEnter Client's Account Number: ");
        accountNumber = scan.nextLine();
        checkerAccountInfo();
    }

    public void checkerAccountInfo() {
        String selectSpecificRecordQuery = "SELECT * FROM account WHERE ACCOUNTNUMBER = '" + accountNumber + "'";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement statement = conn.createStatement()){

            ResultSet results = statement.executeQuery(selectSpecificRecordQuery);

            if (results.next()){
                System.out.println("\nClient's Account Information: "
                        + "\nRemaining Account Balance: " + results.getString("ACCOUNTBALANCE")
                        + "\nAccount Number: " + results.getString("ACCOUNTNUMBER")
                        + "\nFirstname: " + results.getString("FIRSTNAME")
                        + "\nMiddlename: " + results.getString("MIDDLENAME")
                        + "\nLastname: " + results.getString("LASTNAME")
                        + "\nEmail Address: " + results.getString("EMAIL"));

            }else {
                System.out.println("Account doesn't exist.");
                System.out.println("\n[1] Re-Check Account Number\n[2] Back to Main Menu");
                userInputCheckBalMM = scan.nextLine();

                if (userInputCheckBalMM.equals("1")){
                    viewCustomerAccInfo();
                } else if (userInputCheckBalMM.equals("2")) {
                    MainMenu mainMenu = new MainMenu(userAccount);
                    mainMenu.mainMenuAdminModule();
                }else {
                    System.out.println("The menu you wish to access does not exist.");
                }
            }

            System.out.println("\n[1] Back to Main Menu");
            userInputCheckBalMM = scan.nextLine();

            if (userInputCheckBalMM.equals("1")){
                MainMenu mainMenu = new MainMenu(userAccount);
                mainMenu.mainMenuAdminModule();
            } else {
                System.out.println("The menu you wish to access does not exist.");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
