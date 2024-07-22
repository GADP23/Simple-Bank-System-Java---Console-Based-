package org.example.ClientMainMenu;

import org.example.MainMenu;
import org.example.GlobalClass.UserAccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class CheckBankBalance {
    static final String url = "jdbc:mysql://localhost:3306/mti_simple_banking_system";
//    static final String url = "jdbc:mysql://localhost:3306/mti_db";
    static final String username = "root";
    static final String password = "";
    static String userInputCheckBalMM;
    Scanner scan = new Scanner(System.in);


    private UserAccount userAccount;

    public CheckBankBalance(UserAccount userAccount){
        this.userAccount = userAccount;
    }

    public void checkBalance(){
        checkRecord();
        checkBalanceDB();

    }

    public void checkBalanceDB() {
        String selectSpecificRecordQuery = "SELECT * FROM account WHERE ACCOUNTNUMBER = '" + userAccount.getAccNum() + "'";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement statement = conn.createStatement()){

            ResultSet results = statement.executeQuery(selectSpecificRecordQuery);

            if (results.next()){
                System.out.println("\nYour Remaining Account Balance: "
                        + "\nRemaining Account Balance: " + results.getString("ACCOUNTBALANCE")
                        + "\nAccount Number: " + results.getString("ACCOUNTNUMBER")
                        + "\nFirstname: " + results.getString("FIRSTNAME")
                        + "\nMiddlename: " + results.getString("MIDDLENAME")
                        + "\nLastname: " + results.getString("LASTNAME"));

            }else {
                System.out.println("System is Busy.");
            }
            System.out.println("[1] Back to Main Menu");
            userInputCheckBalMM = scan.nextLine();

            if (userInputCheckBalMM.equals("1")){
                MainMenu mainMenu = new MainMenu(userAccount);
                mainMenu.mainMenuClientModule();
            } else {
                System.out.println("The menu you wish to access does not exist.");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void checkRecord(){
        String selectUsernameQuery = "SELECT ac.ACCOUNTNUMBER FROM account ac LEFT JOIN users_account ua ON ua.USERNAME = ac.USERNAME WHERE ua.USERNAME = '" + userAccount.getUsername() + "' AND ua.PASSWORD = '" + userAccount.getPassword() + "';";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement statement = conn.createStatement()){

            ResultSet results = statement.executeQuery(selectUsernameQuery);

            if (results.next()){

            }
            userAccount.setAccNum(results.getString("ACCOUNTNUMBER"));

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
