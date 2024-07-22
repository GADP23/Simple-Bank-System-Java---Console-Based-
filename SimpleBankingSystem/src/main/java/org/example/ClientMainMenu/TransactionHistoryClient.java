package org.example.ClientMainMenu;

import org.example.MainMenu;
import org.example.GlobalClass.Transaction;
import org.example.GlobalClass.UserAccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionHistoryClient {
    static final String url = "jdbc:mysql://localhost:3306/mti_simple_banking_system";
    //    static final String url = "jdbc:mysql://localhost:3306/mti_db";
    static final String username = "root";
    static final String password = "";
    Scanner scan = new Scanner(System.in);
    private static String backMainMenu;

    private UserAccount userAccount;

    public TransactionHistoryClient(UserAccount userAccount){
        this.userAccount = userAccount;

    }
    public List<Transaction> getAllTransactions(){
        String selectSpecificRecordQuery = "SELECT * FROM transaction_history th LEFT JOIN account ac ON ac.ACCOUNTNUMBER = th.ACCOUNTNUMBER  WHERE ac.USERNAME = '" + userAccount.getUsername() + "';";
        List<Transaction> transactions = new ArrayList<>();


        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement statement = conn.createStatement()){

            ResultSet results = statement.executeQuery(selectSpecificRecordQuery);
            while (results.next()) {
                userAccount.setAccNum(results.getString("ACCOUNTNUMBER"));
                System.out.println("Transaction ID: " + results.getInt("TRANSACTIONID") + " Account Number: " + results.getString("ACCOUNTNUMBER") + " Deposit Date: " + results.getTimestamp("DEPOSIT_DATE") + " Withdraw Date: " + results.getTimestamp("WITHDRAW_DATE") + " Ammount: " + results.getFloat("AMOUNT"));
            }

            System.out.println("\n[1] Back to Main Menu");
            backMainMenu = scan.nextLine();

            if (backMainMenu.equals("1")){
                MainMenu mainMenu = new MainMenu(userAccount);
                mainMenu.mainMenuClientModule();
            } else {
                System.out.println("The menu you wish to access does not exist.");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return transactions;
    }
}
